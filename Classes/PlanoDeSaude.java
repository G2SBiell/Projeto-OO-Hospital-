package Classes;
import java.util.HashMap;
import java.util.Map;

public class PlanoDeSaude {
    private String nome;
    
    private Map<Especialidade, Double> descontosPorEspecialidade;

    public PlanoDeSaude(String nome) {
        this.nome = nome;
        this.descontosPorEspecialidade = new HashMap<>();
    }

    public void adicionarDesconto(Especialidade especialidade, double percentual) {
        this.descontosPorEspecialidade.put(especialidade, percentual);
    }

    public double getDesconto(Especialidade especialidade) {
        return this.descontosPorEspecialidade.getOrDefault(especialidade, 0.0);
    }

    public String getNome() {
        return nome;
    }
}