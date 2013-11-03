package ee.ut.math.tvt.salessystem.ui.model;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;

/**
 * Main model. Holds all the other models.
 */
public class SalesSystemModel {

    // Warehouse model
    private StockTableModel warehouseTableModel;
    
    // Current shopping cart model
    private PurchaseInfoTableModel currentPurchaseTableModel;
    
    // History model
    private HistoryTableModel currentHistoryTableModel;

	private final SalesDomainController domainController;

	/**
     * Construct application model.
     * @param domainController Sales domain controller.
     */
    public SalesSystemModel(SalesDomainController domainController) {
        this.domainController = domainController;
        
        warehouseTableModel = new StockTableModel();
        currentPurchaseTableModel = new PurchaseInfoTableModel();
        currentHistoryTableModel = new HistoryTableModel();

        // Populate models with data from the database.
        warehouseTableModel.populateWithData(domainController.loadWarehouseState());
        currentHistoryTableModel.populateWithData(domainController.loadHistoryState());

    }

    public StockTableModel getWarehouseTableModel() {
        return warehouseTableModel;
    }

    public PurchaseInfoTableModel getCurrentPurchaseTableModel() {
        return currentPurchaseTableModel;
    }
    
    public HistoryTableModel getCurrentHistoryTableModel() {
		return currentHistoryTableModel;
	}
    
    public SalesDomainController getDomainController() {
		return domainController;
	}
    
}
