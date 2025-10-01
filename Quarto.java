public class Quarto {
    private int numero;
    private boolean estaOcupado;

    public Quarto(int numero) {
        this.numero = numero;
        this.estaOcupado = false;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isEstaOcupado() {
        return estaOcupado;
    }

    public void ocupar() {
        this.estaOcupado = true;
    }

    public void desocupar() {
        this.estaOcupado = false;
    }
}