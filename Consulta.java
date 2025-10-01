import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consulta {

    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dataHora;
    private StatusConsulta status;
    private String diagnostico;
    private String prescricao;
    private double valorBase;

    public Consulta(Paciente paciente, Medico medico, LocalDateTime dataHora, double valorBase) {
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.status = StatusConsulta.AGENDADA;
        this.diagnostico = "";
        this.prescricao = "";
        this.valorBase = valorBase;
    }
    
    public void finalizar(String diagnostico, String prescricao) {
        this.diagnostico = diagnostico;
        this.prescricao = prescricao;
        this.status = StatusConsulta.FINALIZADA;
    }

    public StatusConsulta getStatus() { return status; }
    public String getDiagnostico() { return diagnostico; }
    public String getPrescricao() { return prescricao; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public LocalDateTime getDataHora() { return dataHora; }
    public double getValorBase() { return valorBase; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
        String dataFormatada = dataHora.format(formatter);

        String detalhes = "Consulta (" + status + ") em " + dataFormatada +
               "\n  - Paciente: " + paciente.getNome() +
               "\n  - Médico: " + medico.getNome() + " (" + medico.getEspecialidade().getNomeAmigavel() + ")";
        
        if (status == StatusConsulta.FINALIZADA) {
            detalhes += "\n  - Diagnóstico: " + diagnostico +
                        "\n  - Prescrição: " + prescricao;
        }
        return detalhes;
    }

    public void salvarEmCSV(String caminhoArquivo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String linha = paciente.getNome() + "," +
                       medico.getNome() + "," +
                       medico.getEspecialidade().getNomeAmigavel() + "," +
                       dataHora.format(formatter) + "," +
                       status + "," +
                       diagnostico.replace(",", ";") + "," + 
                       prescricao.replace(",", ";") + "," +
                       valorBase;

        try (FileWriter writer = new FileWriter(caminhoArquivo, true)) {
            writer.write(linha + "\n");
            System.out.println("Consulta salva no arquivo: " + caminhoArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar consulta: " + e.getMessage());
        }
    }
}
