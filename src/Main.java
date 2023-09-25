import javax.swing.*;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String inputCurrency = SelectCurrencyDialog("Selecciona la moneda a convertir:", 0);
        Float inputAmount = AmountDialog("Ingresa la cantidad a convertir en " + inputCurrency + ":");
        String outputCurrency = SelectCurrencyDialog("Selecciona la moneda a la que se convertirá:", inputCurrency.equals("MXN") ? 1 : 0);
        Float outputAmount = ConvertCurrency(inputAmount, inputCurrency, outputCurrency);
        ShowResult(inputAmount, inputCurrency, outputAmount, outputCurrency);
    }

    public static String SelectCurrencyDialog(String message, Integer initialOption) {
        String[] currencies = {"Pesos Mexicanos (MXN)", "Dólares Estadounidenses (USD)", "Euros (EUR)", "Libras Esterlinas (GBP)", "Yen Japonés (JPY)", "Won Surcoreano (KRW)"};
        try {
            String selected = (String) JOptionPane.showInputDialog(null, message, "Moneda", JOptionPane.QUESTION_MESSAGE, null, currencies, currencies[initialOption]);
            return selected.substring(selected.length() - 4, selected.length() - 1);
        } catch (NullPointerException e) {
            ErrorDialog("No se seleccionó ninguna moneda.");
            return null;
        }
    }

    public static Float AmountDialog(String message) {
        try {
            return Float.parseFloat(JOptionPane.showInputDialog(null, message, "Cantidad", JOptionPane.QUESTION_MESSAGE));
        } catch (NumberFormatException e) {
            ErrorDialog("No se ingresó una cantidad válida.");
            return null;
        }
    }

    public static Float ConvertCurrency(Float amount, String inputCurrency, String outputCurrency) {
        Map<String, Float> exchangeRates = GetExchangeRatesBaseMXN();

        Float inputAmountInMXN = amount / exchangeRates.get(inputCurrency);
        return inputAmountInMXN * exchangeRates.get(outputCurrency);
    }

    public static Map<String, Float> GetExchangeRatesBaseMXN() {
        return Map.of(
                "MXN", 1.0f,
                "USD", 0.058f,
                "EUR", 0.055f,
                "GBP", 0.048f,
                "JPY", 8.63f,
                "KRW", 77.71f
        );
    }

    public static void ErrorDialog(String message) {
        int option = JOptionPane.showConfirmDialog(null, message + "\n¿Deseas reiniciar el programa?", "Error", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            main(null);
        } else {
            System.exit(0);
        }
    }

    public static void ShowResult(Float inputAmount, String inputCurrency, Float outputAmount, String outputCurrency) {
        JOptionPane.showMessageDialog(null, inputAmount + " " + inputCurrency + " = " + outputAmount + " " + outputCurrency, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        int option = JOptionPane.showConfirmDialog(null, "¿Deseas realizar otra conversión?", "Reiniciar", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            main(null);
        } else {
            System.exit(0);
        }
    }
}