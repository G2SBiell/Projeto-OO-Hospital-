
public enum Especialidade {
    PEDIATRIA("Pediatria"),
    NEUROCIRURGIA("Neurocirurgia"),
    CARDIOLOGIA("Cardiologia"),
    ORTOPEDIA("Ortopedia"),
    DERMATOLOGIA("Dermatologia");

    private final String nomeAmigavel;

    Especialidade(String nomeAmigavel) {
        this.nomeAmigavel = nomeAmigavel;
    }

    public String getNomeAmigavel() {
        return nomeAmigavel;
    }
}