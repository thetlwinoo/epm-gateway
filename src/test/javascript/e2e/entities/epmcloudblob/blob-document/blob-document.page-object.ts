import { element, by, ElementFinder } from 'protractor';

export class BlobDocumentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-blob-document div table .btn-danger'));
  title = element.all(by.css('jhi-blob-document div h2#page-heading span')).first();

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

export class BlobDocumentUpdatePage {
  pageTitle = element(by.id('jhi-blob-document-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  documentInput = element(by.id('file_document'));
  lastEditedByInput = element(by.id('field_lastEditedBy'));
  lastEditedWhenInput = element(by.id('field_lastEditedWhen'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDocumentInput(document) {
    await this.documentInput.sendKeys(document);
  }

  async getDocumentInput() {
    return await this.documentInput.getAttribute('value');
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

export class BlobDocumentDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-blobDocument-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-blobDocument'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
