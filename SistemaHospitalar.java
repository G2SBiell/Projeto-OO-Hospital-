import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SistemaHospitalar {

    private List<Paciente> pacientes;
    private List<Medico> medicos;
    private List<Consulta> consultas;
    private List<Quarto> quartos;
    private List<Internacao> internacoes;

    private GerenciadorCSV gerenciadorCSV;

    private static final double DESCONTO_IDADE_60_MAIS = 0.10; 
    private static final double CUSTO_DIARIA_INTERNACAO = 750.0;

    public SistemaHospitalar() {
        this.gerenciadorCSV = new GerenciadorCSV();
        
        this.medicos = gerenciadorCSV.carregarMedicos();
        this.pacientes = gerenciadorCSV.carregarPacientes();
        this.consultas = gerenciadorCSV.carregarConsultas(this.pacientes, this.medicos);

        this.quartos = new ArrayList<>();
        this.internacoes = new ArrayList<>();

        System.out.println("Sistema Hospitalar iniciado. " + medicos.size() + " médicos, " + pacientes.size() + " pacientes e " + consultas.size() + " consultas carregadas.");
    }

    
    public void cadastrarPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        System.out.println("Paciente " + paciente.getNome() + " cadastrado com sucesso.");
    }

    public void cadastrarMedico(Medico medico) {
        this.medicos.add(medico);
        System.out.println("Médico " + medico.getNome() + " cadastrado com sucesso.");
    }

    public void adicionarQuarto(Quarto quarto) {
        this.quartos.add(quarto);
    }

    public boolean agendarConsulta(Consulta consulta) {
        for (Consulta c : this.consultas) {
            if (c.getMedico().equals(consulta.getMedico()) && c.getDataHora().equals(consulta.getDataHora())) {
                System.out.println("ERRO: O Dr(a). " + consulta.getMedico().getNome() + " já possui uma consulta marcada para este horário.");
                return false;
            }
        }
        this.consultas.add(consulta);
        System.out.println("Sucesso! Consulta agendada.");
        return true;
    }

    public void finalizarConsulta(Consulta consulta, String diagnostico, String prescricao) {
        consulta.finalizar(diagnostico, prescricao);
        Paciente paciente = consulta.getPaciente();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = consulta.getDataHora().format(formatter);
        String registroHistorico = String.format("[%s] Consulta com Dr(a). %s: \n   - Diagnóstico: %s", dataFormatada, consulta.getMedico().getNome(), diagnostico);
        paciente.adicionarAoHistorico(registroHistorico);
        System.out.println("\nConsulta finalizada e histórico do paciente atualizado!");
    }

    public Internacao registrarInternacao(Paciente paciente, Medico medico) {
        for (Quarto q : this.quartos) {
            if (!q.isEstaOcupado()) {
                q.ocupar();
                Internacao novaInternacao = new Internacao(paciente, medico, q, LocalDate.now());
                this.internacoes.add(novaInternacao);
                System.out.printf("Internação registrada para %s no quarto %d.\n", paciente.getNome(), q.getNumero());
                return novaInternacao;
            }
        }
        System.out.println("ERRO: Não há quartos disponíveis para a internação.");
        return null;
    }

    public void finalizarInternacao(Internacao internacao, LocalDate dataSaida) {
        long diasInternado = ChronoUnit.DAYS.between(internacao.getDataEntrada(), dataSaida);
        if (diasInternado == 0) diasInternado = 1;
        
        double custoFinal = diasInternado * CUSTO_DIARIA_INTERNACAO;
        
        Paciente paciente = internacao.getPaciente();
        if (paciente instanceof PacientePlano && diasInternado < 7) {
            custoFinal = 0.0;
        }

        internacao.finalizar(dataSaida, custoFinal);
        internacao.getQuarto().desocupar();
        System.out.printf("Internação de %s finalizada. Duração: %d dias. Custo: R$ %.2f\n", paciente.getNome(), diasInternado, custoFinal);
    }

    public double calcularValorFinal(Consulta consulta) {
        double valorFinal = consulta.getValorBase();
        Paciente paciente = consulta.getPaciente();

        if (paciente instanceof PacientePlano) {
            PacientePlano pp = (PacientePlano) paciente;
            double descontoPlano = pp.getPlano().getDesconto(consulta.getMedico().getEspecialidade());
            valorFinal *= (1 - descontoPlano);
        }

        int idade = Period.between(paciente.getDataNascimento(), LocalDate.now()).getYears();
        if (idade > 60) {
            valorFinal *= (1 - DESCONTO_IDADE_60_MAIS);
        }
        return valorFinal;
    }


    public void exibirHistoricoPaciente(Paciente paciente) {
        System.out.println("\n--- Histórico Médico de: " + paciente.getNome() + " ---");
        if (paciente.getHistoricoMedico().isEmpty()) {
            System.out.println("Nenhum registro no histórico.");
        } else {
            paciente.getHistoricoMedico().forEach(System.out::println);
        }
    }

    public void gerarRelatorioPacientesPorMedico(Medico medico) {
        System.out.printf("\n--- Relatório: Pacientes Atendidos por Dr(a). %s ---\n", medico.getNome());
        Set<Paciente> pacientesAtendidos = new HashSet<>();
        for (Consulta c : this.consultas) {
            if (c.getMedico().equals(medico)) {
                pacientesAtendidos.add(c.getPaciente());
            }
        }
        if (pacientesAtendidos.isEmpty()) {
            System.out.println("Nenhum paciente encontrado.");
        } else {
            pacientesAtendidos.forEach(p -> System.out.println("- " + p.getNome()));
        }
    }

    public void gerarRelatorioConsultasPorPeriodo(LocalDate inicio, LocalDate fim) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.printf("\n--- Relatório: Consultas de %s a %s ---\n", inicio.format(formatter), fim.format(formatter));
        boolean encontrou = false;
        for (Consulta c : this.consultas) {
            LocalDate dataConsulta = c.getDataHora().toLocalDate();
            if (!dataConsulta.isBefore(inicio) && !dataConsulta.isAfter(fim)) {
                System.out.println(c.toString() + "\n--------------------");
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhuma consulta encontrada neste período.");
        }
    }

    public void gerarRelatorioOcupacaoQuartos() {
        System.out.println("\n--- Relatório: Ocupação de Quartos ---");
        for (Quarto quarto : this.quartos) {
            if (quarto.isEstaOcupado()) {
                for (Internacao i : this.internacoes) {
                    if (i.getQuarto().equals(quarto) && i.getDataSaida() == null) {
                        System.out.printf("Quarto %d: OCUPADO - Paciente: %s\n", quarto.getNumero(), i.getPaciente().getNome());
                        break;
                    }
                }
            } else {
                System.out.printf("Quarto %d: LIVRE\n", quarto.getNumero());
            }
        }
    }

    public void salvarDados() {
        gerenciadorCSV.salvarMedicos(this.medicos);
        gerenciadorCSV.salvarPacientes(this.pacientes);
        gerenciadorCSV.salvarConsultas(this.consultas);
        System.out.println("Dados salvos com sucesso nos arquivos CSV!");
    }
    
    public List<Medico> getMedicos() {
        return this.medicos;
    }
}