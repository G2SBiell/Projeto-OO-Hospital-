import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
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
                    System.out.println("Funcionalidade ainda não implementada no menu.");
                    break;
                case 8:
                    sistema.gerarRelatorioOcupacaoQuartos();
                    break;
                case 9:
                    System.out.println("Salvando dados e encerrando o programa...");
                    sistema.salvarDados();
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            pressioneEnterParaContinuar(scanner);
        }
    }

    private static void exibirMenu() {
        System.out.println("\n--- Sistema de Gerenciamento Hospitalar ---");
        System.out.println("1. Cadastrar Novo Paciente");
        System.out.println("2. Cadastrar Novo Médico");
        System.out.println("3. Agendar Consulta");
        System.out.println("4. (Mais opções...)");
        System.out.println("8. Gerar Relatório de Ocupação");
        System.out.println("9. Salvar e Sair");
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
        System.out.println("\n--- Cadastro de Novo Paciente ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Data de Nascimento (formato AAAA-MM-DD): ");
        try {
           // LocalDate dataNascimento = LocalDate.parse(scanner.nextLine());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/mm/dd");
            Paciente novoPaciente = new Paciente(nome, cpf, formatter);
            sistema.cadastrarPaciente(novoPaciente);
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data inválido. Use AAAA-MM-DD.");
        }
    }
    
    private static void cadastrarNovoMedico(Scanner scanner, SistemaHospitalar sistema) {
        System.out.println("\n--- Cadastro de Novo Médico ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Data de Nascimento (formato AAAA-MM-DD): ");
        LocalDate dataNascimento = LocalDate.parse(scanner.nextLine());
        System.out.print("CRM: ");
        String crm = scanner.nextLine();
        
        System.out.println("Especialidades Disponíveis: CARDIOLOGIA, ORTOPEDIA, PEDIATRIA, etc.");
        System.out.print("Especialidade: ");
        try {
            Especialidade especialidade = Especialidade.valueOf(scanner.nextLine().toUpperCase());
            Medico novoMedico = new Medico(nome, cpf, dataNascimento, crm, especialidade);
            sistema.cadastrarMedico(novoMedico);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: Especialidade inválida. Tente novamente.");
        }
    }

    private static void pressioneEnterParaContinuar(Scanner scanner) {
        System.out.print("\nPressione Enter para continuar...");
        scanner.nextLine();
    }
}