package pl.panda.trzy.nbp;

import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NbpApiImp implements NbpApi {

    private static final String NBP_API = "http://api.nbp.pl/api/exchangerates/rates/a/{currency}/{dateStart}/{dateEnd}";
    private DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Pattern CURRENCY_PATTERN = Pattern.compile("^[A-Z]{3}$");

    public NbpResponse getExchangeRate(String currency, PeriodType periodType) {
        validateCurrency(currency);
        LocalDateTime dateEnd = LocalDate.now().atStartOfDay();
        String start = DATE_FORMATTER.format(getStartDate(dateEnd, periodType));
        String end = DATE_FORMATTER.format(dateEnd);
        return executeGetRates(currency, start, end);
    }

    public Map<String, NbpResponse> getExchangeRates(Set<String> currencies, PeriodType periodType) {
        validateCurrencies(currencies);
        Map<String, NbpResponse> exchangeRates = new HashMap<>();
        LocalDateTime dateEnd = LocalDate.now().atStartOfDay();
        String start = DATE_FORMATTER.format(getStartDate(dateEnd, periodType));
        String end = DATE_FORMATTER.format(dateEnd);
        List<CompletableFuture<NbpResponse>> futures = currencies.stream().map(c -> getRateAsync(c, start, end))
                .collect(Collectors.toList());
        futures.stream().map(CompletableFuture::join).forEach(response -> exchangeRates.put(response.getCode(), response));
        return exchangeRates;
    }

    private NbpResponse executeGetRates(String currency, String start, String end) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(NBP_API, NbpResponse.class, currency, start, end);

    }

    private void validateCurrencies(Set<String> currencies) {
        currencies.forEach(this::validateCurrency);
    }

    private CompletableFuture<NbpResponse> getRateAsync(String c, String start, String end) {
        return CompletableFuture.supplyAsync(() -> executeGetRates(c, start, end));
    }

    private void validateCurrency(String currency) {
        Matcher matcher = CURRENCY_PATTERN.matcher(currency);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Nieprawidłowy format waluty: " + currency);
        }
    }

    private LocalDateTime getStartDate(LocalDateTime dateEnd, PeriodType periodType) {
        LocalDateTime dateStart;
        switch (periodType) {
            case WEEK: {
                dateStart = dateEnd.minusWeeks(1L);
                break;
            }
            case TWO_WEEKS: {
                dateStart = dateEnd.minusWeeks(2L);
                break;
            }
            case MONTH: {
                dateStart = dateEnd.minusMonths(1L);
                break;
            }
            case QUARTER: {
                dateStart = dateEnd.minusMonths(3L);
                break;
            }
            case HALF_OF_YEAR: {
                dateStart = dateEnd.minusMonths(6L);
                break;
            }
            case YEAR: {
                dateStart = dateEnd.minusYears(1L);
                break;
            }
            default: {
                dateStart = dateEnd;
            }
        }
        return dateStart;
    }
}
