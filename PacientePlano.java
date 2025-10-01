import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PacientePlano extends Paciente {

    private PlanoDeSaude plano;

    public PacientePlano(String nome, String cpf, DateTimeFormatter dataNascimento, PlanoDeSaude plano) {
        super(nome, cpf, dataNascimento);
        this.plano = plano;
    }

    public PlanoDeSaude getPlano() {
        return plano;
    }
}