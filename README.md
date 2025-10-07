# Projeto-OO-Hospital-
# Sistema de Gerenciamento Hospitalar

Este projeto é uma aplicação de console em Java que simula um sistema de gerenciamento para um hospital. O objetivo foi aplicar conceitos avançados de Orientada a Objetos para criar um sistema coeso e extensível.

Funcionalidades Principais
**Cadastro de Entidades:**
    * Cadastro de Pacientes.
    * Cadastro de Médicos com especialidades definidas.
    **Gerenciamento de Consultas:**
    * Agendamento de novas consultas com validação de conflito de horário para médicos.
    * Finalização de consultas com registro de diagnóstico e prescrição.
**Sistema de Internação:**
    * Registro de internações com alocação automática de quartos vagos.
    * Finalização de internações com cálculo de custo.
**Lógica de Faturamento:**
    * Cálculo de valor final de consultas e internações.
    * Aplicação de descontos variados por plano de saúde, especialidade médica e idade do paciente(+60 anos).
**Persistência de Dados:**
    * Todos os dados de médicos, pacientes e consultas são salvos em arquivos `.csv` locais, garantindo que as informações não se percam ao fechar o programa.
**Relatórios:**
    * Geração de relatórios de ocupação de quartos, consultas por período e pacientes atendidos por médico.**Interface Interativa:**
    * Menu de console interativo para que o usuário possa utilizar o sistema de forma amigável.

**Tecnologias Utilizadas**
Java:Linguagem principal do projeto.
Princípios de OO: Herança, Polimorfismo, Encapsulamento e Abstração.
Java Time API Para manipulação de datas e horas (`LocalDate`, `LocalDateTime`).
Git & GitHub:Para versionamento de código.

**Como Executar o Projeto**

Para compilar e executar o projeto em sua máquina local, siga os passos abaixo.

**Importante**: Voçe deve ter o *Java Development Kit (JDK)* versão 17 ou superior instalado e configurado no PATH do sistema.

1.  **Clone o repositório:**
    git clone [https://github.com/seu-usuario/seu-repositorio.git](https://github.com/seu-usuario/seu-repositorio.git)

2.  **Navegue até a pasta do projeto:**
    cd seu-repositorio

3.  **Compile todos os arquivos Java:**
    javac *.java

4.  **Execute o programa principal:**
    java Main
    O menu interativo do sistema será exibido no terminal.
### 👨‍💻 Autor

**GABRIEL PORTACIO CANDEIA COSTA**
