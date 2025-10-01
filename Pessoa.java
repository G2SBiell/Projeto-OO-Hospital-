import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public abstract class Pessoa {
    
    protected String nome;
    protected String cpf;
    protected DateTimeFormatter dataNascimento;

    public Pessoa(String nome, String cpf, DateTimeFormatter dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
    
    public DateTimeFormatter getDataNascimento() {
        return dataNascimento;
    }

    protected abstract Especialidade getEspecialidade();
}