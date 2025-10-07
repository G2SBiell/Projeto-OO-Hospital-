package Classes;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class Paciente extends Pessoa {
    
    private List<String> historicoMedico;

    public Paciente(String nome, String cpf, LocalDate dataNascimento) {
        super(nome, cpf, dataNascimento);
        this.historicoMedico = new ArrayList<>();
    }

    public void adicionarAoHistorico(String registro) {
        this.historicoMedico.add(registro);
    }
    
    public List<String> getHistoricoMedico() {
        return historicoMedico;
    }

    @Override
    protected Especialidade getEspecialidade() {
        throw new UnsupportedOperationException("Unimplemented method 'getEspecialidade'");
    }

    public void salvarEmCSV(String caminhoArquivo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateText = getDataNascimento().format(formatter);
        
        String historico = String.join(";", historicoMedico);

        String linha = getNome() + "," +
                       getCpf() + "," +
                       LocalDate.parse(dateText, formatter) + "," +
                       historico;

        try (FileWriter writer = new FileWriter(caminhoArquivo, true)) {
            writer.write(linha + "\n");
            System.out.println("Paciente salvo no arquivo: " + caminhoArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar paciente: " + e.getMessage());
        }
    }
}
