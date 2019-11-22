import { element, by, ElementFinder } from 'protractor';

export class ProductDocumentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product-document div table .btn-danger'));
  title = element.all(by.css('jhi-product-document div h2#page-heading span')).first();

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

export class ProductDocumentUpdatePage {
  pageTitle = element(by.id('jhi-product-document-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  videoUrlInput = element(by.id('field_videoUrl'));
  highlightsUrlInput = element(by.id('field_highlightsUrl'));
  longDescriptionUrlInput = element(by.id('field_longDescriptionUrl'));
  shortDescriptionUrlInput = element(by.id('field_shortDescriptionUrl'));
  descriptionUrlInput = element(by.id('field_descriptionUrl'));
  careInstructionsUrlInput = element(by.id('field_careInstructionsUrl'));
  specialFeaturesUrlInput = element(by.id('field_specialFeaturesUrl'));
  usageAndSideEffectsUrlInput = element(by.id('field_usageAndSideEffectsUrl'));
  safetyWarnningUrlInput = element(by.id('field_safetyWarnningUrl'));
  productTypeInput = element(by.id('field_productType'));
  modelNameInput = element(by.id('field_modelName'));
  modelNumberInput = element(by.id('field_modelNumber'));
  fabricTypeInput = element(by.id('field_fabricType'));
  productComplianceCertificateInput = element(by.id('field_productComplianceCertificate'));
  genuineAndLegalInput = element(by.id('field_genuineAndLegal'));
  countryOfOriginInput = element(by.id('field_countryOfOrigin'));
  warrantyPeriodInput = element(by.id('field_warrantyPeriod'));
  warrantyPolicyInput = element(by.id('field_warrantyPolicy'));
  warrantyTypeSelect = element(by.id('field_warrantyType'));
  cultureSelect = element(by.id('field_culture'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setVideoUrlInput(videoUrl) {
    await this.videoUrlInput.sendKeys(videoUrl);
  }

  async getVideoUrlInput() {
    return await this.videoUrlInput.getAttribute('value');
  }

  async setHighlightsUrlInput(highlightsUrl) {
    await this.highlightsUrlInput.sendKeys(highlightsUrl);
  }

  async getHighlightsUrlInput() {
    return await this.highlightsUrlInput.getAttribute('value');
  }

  async setLongDescriptionUrlInput(longDescriptionUrl) {
    await this.longDescriptionUrlInput.sendKeys(longDescriptionUrl);
  }

  async getLongDescriptionUrlInput() {
    return await this.longDescriptionUrlInput.getAttribute('value');
  }

  async setShortDescriptionUrlInput(shortDescriptionUrl) {
    await this.shortDescriptionUrlInput.sendKeys(shortDescriptionUrl);
  }

  async getShortDescriptionUrlInput() {
    return await this.shortDescriptionUrlInput.getAttribute('value');
  }

  async setDescriptionUrlInput(descriptionUrl) {
    await this.descriptionUrlInput.sendKeys(descriptionUrl);
  }

  async getDescriptionUrlInput() {
    return await this.descriptionUrlInput.getAttribute('value');
  }

  async setCareInstructionsUrlInput(careInstructionsUrl) {
    await this.careInstructionsUrlInput.sendKeys(careInstructionsUrl);
  }

  async getCareInstructionsUrlInput() {
    return await this.careInstructionsUrlInput.getAttribute('value');
  }

  async setSpecialFeaturesUrlInput(specialFeaturesUrl) {
    await this.specialFeaturesUrlInput.sendKeys(specialFeaturesUrl);
  }

  async getSpecialFeaturesUrlInput() {
    return await this.specialFeaturesUrlInput.getAttribute('value');
  }

  async setUsageAndSideEffectsUrlInput(usageAndSideEffectsUrl) {
    await this.usageAndSideEffectsUrlInput.sendKeys(usageAndSideEffectsUrl);
  }

  async getUsageAndSideEffectsUrlInput() {
    return await this.usageAndSideEffectsUrlInput.getAttribute('value');
  }

  async setSafetyWarnningUrlInput(safetyWarnningUrl) {
    await this.safetyWarnningUrlInput.sendKeys(safetyWarnningUrl);
  }

  async getSafetyWarnningUrlInput() {
    return await this.safetyWarnningUrlInput.getAttribute('value');
  }

  async setProductTypeInput(productType) {
    await this.productTypeInput.sendKeys(productType);
  }

  async getProductTypeInput() {
    return await this.productTypeInput.getAttribute('value');
  }

  async setModelNameInput(modelName) {
    await this.modelNameInput.sendKeys(modelName);
  }

  async getModelNameInput() {
    return await this.modelNameInput.getAttribute('value');
  }

  async setModelNumberInput(modelNumber) {
    await this.modelNumberInput.sendKeys(modelNumber);
  }

  async getModelNumberInput() {
    return await this.modelNumberInput.getAttribute('value');
  }

  async setFabricTypeInput(fabricType) {
    await this.fabricTypeInput.sendKeys(fabricType);
  }

  async getFabricTypeInput() {
    return await this.fabricTypeInput.getAttribute('value');
  }

  async setProductComplianceCertificateInput(productComplianceCertificate) {
    await this.productComplianceCertificateInput.sendKeys(productComplianceCertificate);
  }

  async getProductComplianceCertificateInput() {
    return await this.productComplianceCertificateInput.getAttribute('value');
  }

  getGenuineAndLegalInput(timeout?: number) {
    return this.genuineAndLegalInput;
  }
  async setCountryOfOriginInput(countryOfOrigin) {
    await this.countryOfOriginInput.sendKeys(countryOfOrigin);
  }

  async getCountryOfOriginInput() {
    return await this.countryOfOriginInput.getAttribute('value');
  }

  async setWarrantyPeriodInput(warrantyPeriod) {
    await this.warrantyPeriodInput.sendKeys(warrantyPeriod);
  }

  async getWarrantyPeriodInput() {
    return await this.warrantyPeriodInput.getAttribute('value');
  }

  async setWarrantyPolicyInput(warrantyPolicy) {
    await this.warrantyPolicyInput.sendKeys(warrantyPolicy);
  }

  async getWarrantyPolicyInput() {
    return await this.warrantyPolicyInput.getAttribute('value');
  }

  async warrantyTypeSelectLastOption(timeout?: number) {
    await this.warrantyTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async warrantyTypeSelectOption(option) {
    await this.warrantyTypeSelect.sendKeys(option);
  }

  getWarrantyTypeSelect(): ElementFinder {
    return this.warrantyTypeSelect;
  }

  async getWarrantyTypeSelectedOption() {
    return await this.warrantyTypeSelect.element(by.css('option:checked')).getText();
  }

  async cultureSelectLastOption(timeout?: number) {
    await this.cultureSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async cultureSelectOption(option) {
    await this.cultureSelect.sendKeys(option);
  }

  getCultureSelect(): ElementFinder {
    return this.cultureSelect;
  }

  async getCultureSelectedOption() {
    return await this.cultureSelect.element(by.css('option:checked')).getText();
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

export class ProductDocumentDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-productDocument-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-productDocument'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
