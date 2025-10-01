import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Medico extends Pessoa {

    private String crm;
    private Especialidade especialidade;

    public Medico(String nome, String cpf, LocalDate dataNascimento, String crm, Especialidade especialidade) {
        super(nome, cpf, dataNascimento);
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public String getCrm() {
        return crm;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void salvarEmCSV(String caminhoArquivo) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String textDate = getDataNascimento().format(formatter)
        LocalDate parsedDate = LocalDate.parse(textDate, formatter)
        
        String linha = getNome() + "," +
                       getCpf() + "," +
                       parsedDate + "," +
                       crm + "," +
                       especialidade.getNomeAmigavel();

        try (FileWriter writer = new FileWriter(caminhoArquivo, true)) {
            writer.write(linha + "\n");
            System.out.println("Médico salvo no arquivo: " + caminhoArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar médico: " + e.getMessage());
        }
    }
}
