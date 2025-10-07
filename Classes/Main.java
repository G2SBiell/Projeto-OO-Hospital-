package Classes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SistemaHospitalar sistema = new SistemaHospitalar();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            exibirMenu();
            int opcao = lerOpcao(scanner);

            switch (opcao) {
                case 1:
                    cadastrarNovoPaciente(scanner, sistema);
                    break;
                case 2:
                    cadastrarNovoMedico(scanner, sistema);
                    break;
                case 3:
                    agendarNovaConsulta(scanner, sistema);
                    break;
                case 4:
                    finalizarConsultaPeloMenu(scanner, sistema);
                    break;
                case 5:
                    sistema.gerarRelatorioOcupacaoQuartos();
                    break;
                case 6:
                    System.out.println("Salvando dados e encerrando o programa");
                    sistema.salvarDados();
                    scanner.close();
                    return;
                default:
                    System.out.println("Inválida. Tente novamente.");
            }
            pressioneEnterParaContinuar(scanner);
        }
    }

    private static void exibirMenu() {
        System.out.println("\n Sistema de Gerenciamento Hospitalar");
        System.out.println("1. Cadastrar Novo Paciente");
        System.out.println("2. Cadastrar Novo Médico");
        System.out.println("3. Agendar Consulta");
        System.out.println("4. Finalizar consulta");
        System.out.println("5. Gerar Relatório de Ocupação");
        System.out.println("6. Salvar e Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao(Scanner scanner) {
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();
            return opcao;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -1;
        }
    }

    private static void cadastrarNovoPaciente(Scanner scanner, SistemaHospitalar sistema) {
        System.out.println("\nCadastro de Novo Paciente");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Data de Nascimento (separe os numeros por barra(/)): ");
        try {


            String dataNascStr = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataNascimentoFormatado  =LocalDate.parse(dataNascStr, formatter);

            Paciente novoPaciente = new Paciente(nome, cpf, dataNascimentoFormatado);
            sistema.cadastrarPaciente(novoPaciente);

        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data inválido. Use dd/MM/yyyy.");
        }
    }
    
    private static void cadastrarNovoMedico(Scanner scanner, SistemaHospitalar sistema) {
        System.out.println("\n Cadastro de Novo Médico");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Data de Nascimento (separe os numeros por barra(/)): ");

        String dataNascimento = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNascimentoFormatado = LocalDate.parse(dataNascimento, formatter);

        System.out.print("CRM: ");
        String crm = scanner.nextLine();
        
        System.out.println("Especialidades Disponíveis: Cardiologia, Ortopedia, Pediatria, Neurocirurgia e Demartologia");
        System.out.print("Especialidade:");
        try {
            Especialidade especialidade = Especialidade.valueOf(scanner.nextLine().toUpperCase());
            Medico novoMedico = new Medico(nome, cpf, dataNascimentoFormatado, crm, especialidade);
            sistema.cadastrarMedico(novoMedico);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: Especialidade inválida. Tente novamente.");
        }
    }

    private static void pressioneEnterParaContinuar(Scanner scanner) {
        System.out.print("\nPressione Enter para continuar...");
        scanner.nextLine();
    }
    private static void agendarNovaConsulta(Scanner scanner, SistemaHospitalar sistema) {
    System.out.println("\n Agendamento de Nova Consulta");

    if (sistema.getPacientes().isEmpty() || sistema.getMedicos().isEmpty()) {
        System.out.println("ERRO: É preciso ter ao menos um paciente e um médico cadastrados para agendar uma consulta.");
        return;
    }

    System.out.println("Selecione o paciente:");
    List<Paciente> pacientes = sistema.getPacientes();
    for (int i = 0; i < pacientes.size(); i++) {
        System.out.printf("%d. %s\n", (i + 1), pacientes.get(i).getNome());
    }
    System.out.print("Digite o número do paciente: ");
    int escolhaPaciente = scanner.nextInt();
    scanner.nextLine();
    Paciente pacienteEscolhido = pacientes.get(escolhaPaciente - 1);

    System.out.println("\nSelecione o médico:");
    List<Medico> medicos = sistema.getMedicos();
    for (int i = 0; i < medicos.size(); i++) {
        System.out.printf("%d. %s (%s)\n", (i + 1), medicos.get(i).getNome(), medicos.get(i).getEspecialidade().getNomeAmigavel());
    }
    System.out.print("Digite o número do médico: ");
    int escolhaMedico = scanner.nextInt();
    scanner.nextLine();
    Medico medicoEscolhido = medicos.get(escolhaMedico - 1);

    try {
        System.out.print("Digite a data e hora (formato AAAA-MM-DDTHH:MM, ex: 2025-10-28T14:30): ");
        LocalDateTime dataHora = LocalDateTime.parse(scanner.nextLine());

        System.out.print("Digite o valor base da consulta (ex: 250.0): ");
        double valorBase = scanner.nextDouble();
        scanner.nextLine();
        Consulta novaConsulta = new Consulta(pacienteEscolhido, medicoEscolhido, dataHora, valorBase);
        sistema.agendarConsulta(novaConsulta);

    } catch (DateTimeParseException e) {
        System.out.println("ERRO: Formato de data e hora inválido. Agendamento cancelado.");
    } catch (InputMismatchException e) {
        System.out.println("ERRO: Valor da consulta inválido. Agendamento cancelado.");
    } catch (IndexOutOfBoundsException e) {
        System.out.println("ERRO: Escolha inválida. Agendamento cancelado.");
    }
    }

    private static void finalizarConsultaPeloMenu(Scanner scanner, SistemaHospitalar sistema) {
    System.out.println("\n Finalizar Consulta");

    List<Consulta> consultasAgendadas = new ArrayList<>();
    for (Consulta c : sistema.getConsultas()) { 
        if (c.getStatus() == StatusConsulta.AGENDADA) {
            consultasAgendadas.add(c);
        }
    }

    if (consultasAgendadas.isEmpty()) {
        System.out.println("Nenhuma consulta agendada para finalizar.");
        return;
    }

    System.out.println("Selecione a consulta para finalizar:");
    for (int i = 0; i < consultasAgendadas.size(); i++) {
        Consulta c = consultasAgendadas.get(i);
        System.out.printf("%d. Paciente: %s | Médico: %s | Data: %s\n",
                (i + 1),
                c.getPaciente().getNome(),
                c.getMedico().getNome(),
                c.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }

    try {
        System.out.print("Digite o número da consulta: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        Consulta consultaParaFinalizar = consultasAgendadas.get(escolha - 1);

        System.out.print("Digite o diagnóstico: ");
        String diagnostico = scanner.nextLine();
        System.out.print("Digite a prescrição: ");
        String prescricao = scanner.nextLine();

        sistema.finalizarConsulta(consultaParaFinalizar, diagnostico, prescricao);

    } 
        catch (Exception e) {
        System.out.println("Erro: Opção inválida ou dados incorretos. Operação cancelada.");
    }
}
}