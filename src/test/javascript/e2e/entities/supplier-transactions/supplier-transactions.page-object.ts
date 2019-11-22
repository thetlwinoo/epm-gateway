import { element, by, ElementFinder } from 'protractor';

export class SupplierTransactionsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-supplier-transactions div table .btn-danger'));
  title = element.all(by.css('jhi-supplier-transactions div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class SupplierTransactionsUpdatePage {
  pageTitle = element(by.id('jhi-supplier-transactions-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  supplierInvoiceNumberInput = element(by.id('field_supplierInvoiceNumber'));
  transactionDateInput = element(by.id('field_transactionDate'));
  amountExcludingTaxInput = element(by.id('field_amountExcludingTax'));
  taxAmountInput = element(by.id('field_taxAmount'));
  transactionAmountInput = element(by.id('field_transactionAmount'));
  outstandingBalanceInput = element(by.id('field_outstandingBalance'));
  finalizationDateInput = element(by.id('field_finalizationDate'));
  isFinalizedInput = element(by.id('field_isFinalized'));
  lastEditedByInput = element(by.id('field_lastEditedBy'));
  lastEditedWhenInput = element(by.id('field_lastEditedWhen'));
  supplierSelect = element(by.id('field_supplier'));
  transactionTypeSelect = element(by.id('field_transactionType'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSupplierInvoiceNumberInput(supplierInvoiceNumber) {
    await this.supplierInvoiceNumberInput.sendKeys(supplierInvoiceNumber);
  }

  async getSupplierInvoiceNumberInput() {
    return await this.supplierInvoiceNumberInput.getAttribute('value');
  }

  async setTransactionDateInput(transactionDate) {
    await this.transactionDateInput.sendKeys(transactionDate);
  }

  async getTransactionDateInput() {
    return await this.transactionDateInput.getAttribute('value');
  }

  async setAmountExcludingTaxInput(amountExcludingTax) {
    await this.amountExcludingTaxInput.sendKeys(amountExcludingTax);
  }

  async getAmountExcludingTaxInput() {
    return await this.amountExcludingTaxInput.getAttribute('value');
  }

  async setTaxAmountInput(taxAmount) {
    await this.taxAmountInput.sendKeys(taxAmount);
  }

  async getTaxAmountInput() {
    return await this.taxAmountInput.getAttribute('value');
  }

  async setTransactionAmountInput(transactionAmount) {
    await this.transactionAmountInput.sendKeys(transactionAmount);
  }

  async getTransactionAmountInput() {
    return await this.transactionAmountInput.getAttribute('value');
  }

  async setOutstandingBalanceInput(outstandingBalance) {
    await this.outstandingBalanceInput.sendKeys(outstandingBalance);
  }

  async getOutstandingBalanceInput() {
    return await this.outstandingBalanceInput.getAttribute('value');
  }

  async setFinalizationDateInput(finalizationDate) {
    await this.finalizationDateInput.sendKeys(finalizationDate);
  }

  async getFinalizationDateInput() {
    return await this.finalizationDateInput.getAttribute('value');
  }

  getIsFinalizedInput(timeout?: number) {
    return this.isFinalizedInput;
  }
  async setLastEditedByInput(lastEditedBy) {
    await this.lastEditedByInput.sendKeys(lastEditedBy);
  }

  async getLastEditedByInput() {
    return await this.lastEditedByInput.getAttribute('value');
  }

  async setLastEditedWhenInput(lastEditedWhen) {
    await this.lastEditedWhenInput.sendKeys(lastEditedWhen);
  }

  async getLastEditedWhenInput() {
    return await this.lastEditedWhenInput.getAttribute('value');
  }

  async supplierSelectLastOption(timeout?: number) {
    await this.supplierSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async supplierSelectOption(option) {
    await this.supplierSelect.sendKeys(option);
  }

  getSupplierSelect(): ElementFinder {
    return this.supplierSelect;
  }

  async getSupplierSelectedOption() {
    return await this.supplierSelect.element(by.css('option:checked')).getText();
  }

  async transactionTypeSelectLastOption(timeout?: number) {
    await this.transactionTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async transactionTypeSelectOption(option) {
    await this.transactionTypeSelect.sendKeys(option);
  }

  getTransactionTypeSelect(): ElementFinder {
    return this.transactionTypeSelect;
  }

  async getTransactionTypeSelectedOption() {
    return await this.transactionTypeSelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class SupplierTransactionsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-supplierTransactions-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-supplierTransactions'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
