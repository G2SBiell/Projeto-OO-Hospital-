import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorCSV {
    private static final String MEDICO_CSV = "medicos.csv";
    private static final String PACIENTE_CSV = "pacientes.csv";
    private static final String CONSULTA_CSV = "consultas.csv";

    public void salvarMedicos(List<Medico> medicos) {
        List<String> linhas = medicos.stream()
                .map(m -> String.join(",", m.getCpf(), m.getNome(), m.getDataNascimento().toString(), m.getCrm(), m.getEspecialidade().name()))
                .collect(Collectors.toList());
        
        linhas.add(0, "cpf,nome,dataNascimento,crm,especialidade");
        
        escreverArquivo(MEDICO_CSV, linhas);
    }

    public void salvarPacientes(List<Paciente> pacientes) {
        List<String> linhas = pacientes.stream()
                .map(p -> {
                    if (p instanceof PacientePlano) {
                        PacientePlano pp = (PacientePlano) p;
                        return String.join(",", p.getCpf(), p.getNome(), p.getDataNascimento().toString(), "PLANO", pp.getPlano().getNome());
                    } else {
                        return String.join(",", p.getCpf(), p.getNome(), p.getDataNascimento().toString(), "COMUM");
                    }
                })
                .collect(Collectors.toList());
        
        linhas.add(0, "cpf,nome,dataNascimento,tipo,planoNome");
        escreverArquivo(PACIENTE_CSV, linhas);
    }

    public void salvarConsultas(List<Consulta> consultas) {
        List<String> linhas = consultas.stream()
                .map(c -> String.join(",", 
                    c.getPaciente().getCpf(), 
                    c.getMedico().getCpf(), 
                    c.getDataHora().toString(), 
                    String.valueOf(c.getValorBase())))
                .collect(Collectors.toList());

        linhas.add(0, "pacienteCpf,medicoCpf,dataHora,valorBase");
        escreverArquivo(CONSULTA_CSV, linhas);
    }
    
    public List<Medico> carregarMedicos() {
        List<String> linhas = lerArquivo(MEDICO_CSV);
        if (linhas.isEmpty()) return new ArrayList<>();

        return linhas.stream().skip(1)
                .map(linha -> {
                    String[] campos = linha.split(",");
                    return new Medico(campos[1], campos[0], campos[2].toString() , campos[3], Especialidade.valueOf(campos[4]));
                })
                .collect(Collectors.toList());
    }

    public List<Paciente> carregarPacientes() {
        List<String> linhas = lerArquivo(PACIENTE_CSV);
        if (linhas.isEmpty()) return new ArrayList<>();

        return linhas.stream().skip(1)
                .map(linha -> {
                    String[] campos = linha.split(",");
                    if (campos[3].equals("PLANO")) {
                        PlanoDeSaude plano = new PlanoDeSaude(campos[4]);
                        return new PacientePlano(campos[1], campos[0], LocalDate.parse(campos[2]), plano);
                    } else {
                        return new Paciente(campos[1], campos[0], LocalDate.parse(campos[2]));
                    }
                })
                .collect(Collectors.toList());
    }
    
    public List<Consulta> carregarConsultas(List<Paciente> pacientes, List<Medico> medicos) {
        List<String> linhas = lerArquivo(CONSULTA_CSV);
        if (linhas.isEmpty()) return new ArrayList<>();

        return linhas.stream().skip(1)
                .map(linha -> {
                    String[] campos = linha.split(",");
                    String pacienteCpf = campos[0];
                    String medicoCpf = campos[1];
                    
                    Paciente paciente = pacientes.stream().filter(p -> p.getCpf().equals(pacienteCpf)).findFirst().orElse(null);
                    Medico medico = medicos.stream().filter(m -> m.getCpf().equals(medicoCpf)).findFirst().orElse(null);

                    if (paciente != null && medico != null) {
                        return new Consulta(paciente, medico, LocalDateTime.parse(campos[2]), Double.parseDouble(campos[3]));
                    }
                    return null;
                })
                .filter(c -> c != null)
                .collect(Collectors.toList());
    }

    private void escreverArquivo(String nomeArquivo, List<String> linhas) {
        try {
            Path arquivo = Paths.get(nomeArquivo);
            Files.write(arquivo, linhas);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo " + nomeArquivo + ": " + e.getMessage());
        }
    }

    private List<String> lerArquivo(String nomeArquivo) {
        try {
            Path arquivo = Paths.get(nomeArquivo);
            if (Files.exists(arquivo)) {
                return Files.readAllLines(arquivo);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo " + nomeArquivo + ": " + e.getMessage());
        }
        return new ArrayList<>(); // Retorna lista vazia se o arquivo não existe ou deu erro
    }
}