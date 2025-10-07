package Classes;
import java.time.LocalDate;

public class Internacao {
    private Paciente paciente;
    private Medico medicoResponsavel;
    private Quarto quarto;
    private LocalDate dataEntrada;
    private LocalDate dataSaida; 
    private double custoTotal;

    public Internacao(Paciente paciente, Medico medicoResponsavel, Quarto quarto, LocalDate dataEntrada) {
        this.paciente = paciente;
        this.medicoResponsavel = medicoResponsavel;
        this.quarto = quarto;
        this.dataEntrada = dataEntrada;
    }
    
    public void finalizar(LocalDate dataSaida, double custoTotal) {
        this.dataSaida = dataSaida;
        this.custoTotal = custoTotal;
    }

    public Paciente getPaciente() { return paciente; }
    public Medico getMedicoResponsavel() { return medicoResponsavel; }
    public Quarto getQuarto() { return quarto; }
    public LocalDate getDataEntrada() { return dataEntrada; }
    public LocalDate getDataSaida() { return dataSaida; }
    public double getCustoTotal() { return custoTotal; }
}