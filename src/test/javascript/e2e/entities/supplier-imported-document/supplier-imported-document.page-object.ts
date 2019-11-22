import { element, by, ElementFinder } from 'protractor';

export class SupplierImportedDocumentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-supplier-imported-document div table .btn-danger'));
  title = element.all(by.css('jhi-supplier-imported-document div h2#page-heading span')).first();

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

export class SupplierImportedDocumentUpdatePage {
  pageTitle = element(by.id('jhi-supplier-imported-document-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  documentUrlInput = element(by.id('field_documentUrl'));
  documentTypeSelect = element(by.id('field_documentType'));
  lastEditedByInput = element(by.id('field_lastEditedBy'));
  lastEditedWhenInput = element(by.id('field_lastEditedWhen'));
  uploadTransactionSelect = element(by.id('field_uploadTransaction'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDocumentUrlInput(documentUrl) {
    await this.documentUrlInput.sendKeys(documentUrl);
  }

  async getDocumentUrlInput() {
    return await this.documentUrlInput.getAttribute('value');
  }

  async setDocumentTypeSelect(documentType) {
    await this.documentTypeSelect.sendKeys(documentType);
  }

  async getDocumentTypeSelect() {
    return await this.documentTypeSelect.element(by.css('option:checked')).getText();
  }

  async documentTypeSelectLastOption(timeout?: number) {
    await this.documentTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
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

  async uploadTransactionSelectLastOption(timeout?: number) {
    await this.uploadTransactionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async uploadTransactionSelectOption(option) {
    await this.uploadTransactionSelect.sendKeys(option);
  }

  getUploadTransactionSelect(): ElementFinder {
    return this.uploadTransactionSelect;
  }

  async getUploadTransactionSelectedOption() {
    return await this.uploadTransactionSelect.element(by.css('option:checked')).getText();
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

export class SupplierImportedDocumentDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-supplierImportedDocument-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-supplierImportedDocument'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
