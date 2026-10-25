package dio.budgeting.application;

import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CalculateBurnRateUseCase {

    private final TransactionRepository repository;

    public CalculateBurnRateUseCase(TransactionRepository repository) {
        this.repository = repository;
    }

    public record BurnRateReport(long currentSpend, double dailyAverage, double projectedMonthlySpend, String status) {}

    @Tool(name = "calculateBurnRate", description = "Calcula o ritmo de gastos atual (burn rate) e projeta qual será o gasto total no final do mês")
    public BurnRateReport execute() {
        List<Transaction> transactions = repository.findAll();
        
        long totalSpend = transactions.stream()
                .mapToLong(Transaction::getAmount)
                .sum();

        LocalDate today = LocalDate.now();
        int currentDay = today.getDayOfMonth();
        int daysInMonth = today.lengthOfMonth();

        // Evita divisão por zero no primeiro dia do mês
        double dailyAverage = currentDay > 0 ? (double) totalSpend / currentDay : 0;
        double projectedTotal = dailyAverage * daysInMonth;

        // Classifica o ritmo do orçamento (valores fictícios em centavos para análise)
        String status;
        if (projectedTotal > 500000) { // Ex: R$ 5.000,00
            status = "ACELERADO: Projeção de gastos acima do teto de alerta.";
        } else {
            status = "ESTÁVEL: Gastos dentro da normalidade para o período.";
        }

        return new BurnRateReport(totalSpend, dailyAverage, projectedTotal, status);
    }
}