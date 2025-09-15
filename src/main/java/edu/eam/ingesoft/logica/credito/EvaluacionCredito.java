package edu.eam.ingesoft.logica.credito;

/**
 * Clase que representa una evaluación de crédito para la entidad financiera FinAurora.
 * Permite calcular cuotas mensuales y evaluar la aprobación de créditos según reglas de negocio.
 */
public class EvaluacionCredito {

    private String nombreSolicitante;
    private double ingresosMensuales;
    private int numeroCreditosActivos;
    private int puntajeCredito;
    private double valorCreditoSolicitado;
    private boolean tieneCodedor;

    /**
     * Constructor de la clase EvaluacionCredito.
     */
    public EvaluacionCredito(String nombreSolicitante, double ingresosMensuales,
                             int numeroCreditosActivos, int puntajeCredito,
                             double valorCreditoSolicitado, boolean tieneCodedor) {
        this.nombreSolicitante = nombreSolicitante;
        this.ingresosMensuales = ingresosMensuales;
        this.numeroCreditosActivos = numeroCreditosActivos;
        this.puntajeCredito = puntajeCredito;
        this.valorCreditoSolicitado = valorCreditoSolicitado;
        this.tieneCodedor = tieneCodedor;
    }

    /**
     * Calcula la tasa de interés mensual a partir de la tasa nominal anual.
     *
     * @param tasaNominalAnual Tasa nominal anual en porcentaje
     * @return Tasa mensual en porcentaje
     */
    public double calcularTasaMensual(double tasaNominalAnual) {
        // Se divide entre 12 porque es mensual y entre 100 porque viene en porcentaje
        return tasaNominalAnual / 12 / 100.0;
    }

    /**
     * Calcula la cuota mensual del crédito usando la fórmula de amortización francesa.
     * Formula: Cuota = M * (im * (1+im)^n) / ((1+im)^n - 1)
     */
    public double calcularCuotaMensual(double tasaNominalAnual, int plazoMeses) {
        double im = calcularTasaMensual(tasaNominalAnual); // interés mensual
        if (im == 0) {
            // Si la tasa es 0, la cuota es simplemente monto / plazo
            return valorCreditoSolicitado / plazoMeses;
        }
        return valorCreditoSolicitado * (im * Math.pow(1 + im, plazoMeses)) /
                (Math.pow(1 + im, plazoMeses) - 1);
    }

    /**
     * Evalúa si el crédito debe ser aprobado según las reglas de negocio:
     * - Perfil bajo (puntaje < 500): Rechazo automático
     * - Perfil medio (500 ≤ puntaje ≤ 700): Requiere codeudor y cuota ≤ 25% de ingresos
     * - Perfil alto (puntaje > 700 y < 2 créditos): Cuota ≤ 30% de ingresos
     */
    public boolean evaluarAprobacion(double tasaNominalAnual, int plazoMeses) {
        double cuota = calcularCuotaMensual(tasaNominalAnual, plazoMeses);

        // Perfil bajo
        if (puntajeCredito < 500) {
            return false;
        }

        // Perfil medio
        if (puntajeCredito >= 500 && puntajeCredito <= 700) {
            return tieneCodedor && (cuota <= ingresosMensuales * 0.25);
        }

        // Perfil alto
        if (puntajeCredito > 700 && numeroCreditosActivos < 2) {
            return cuota <= ingresosMensuales * 0.30;
        }

        // Si no entra en ningún caso, se rechaza
        return false;
    }

    // Getters y setters

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public double getIngresosMensuales() {
        return ingresosMensuales;
    }

    public void setIngresosMensuales(double ingresosMensuales) {
        this.ingresosMensuales = ingresosMensuales;
    }

    public int getNumeroCreditosActivos() {
        return numeroCreditosActivos;
    }

    public void setNumeroCreditosActivos(int numeroCreditosActivos) {
        this.numeroCreditosActivos = numeroCreditosActivos;
    }

    public int getPuntajeCredito() {
        return puntajeCredito;
    }

    public void setPuntajeCredito(int puntajeCredito) {
        this.puntajeCredito = puntajeCredito;
    }

    public double getValorCreditoSolicitado() {
        return valorCreditoSolicitado;
    }

    public void setValorCreditoSolicitado(double valorCreditoSolicitado) {
        this.valorCreditoSolicitado = valorCreditoSolicitado;
    }

    public boolean isTieneCodedor() {
        return tieneCodedor;
    }

    public void setTieneCodedor(boolean tieneCodedor) {
        this.tieneCodedor = tieneCodedor;
    }
}
