import { element, by, ElementFinder } from 'protractor';

export class PhotosComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-photos div table .btn-danger'));
  title = element.all(by.css('jhi-photos div h2#page-heading span')).first();

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

export class PhotosUpdatePage {
  pageTitle = element(by.id('jhi-photos-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  thumbnailImageUrlInput = element(by.id('field_thumbnailImageUrl'));
  originalImageUrlInput = element(by.id('field_originalImageUrl'));
  bannerTallImageUrlInput = element(by.id('field_bannerTallImageUrl'));
  bannerWideImageUrlInput = element(by.id('field_bannerWideImageUrl'));
  circleImageUrlInput = element(by.id('field_circleImageUrl'));
  sharpenedImageUrlInput = element(by.id('field_sharpenedImageUrl'));
  squareImageUrlInput = element(by.id('field_squareImageUrl'));
  watermarkImageUrlInput = element(by.id('field_watermarkImageUrl'));
  priorityInput = element(by.id('field_priority'));
  defaultIndInput = element(by.id('field_defaultInd'));
  stockItemSelect = element(by.id('field_stockItem'));
  productCategorySelect = element(by.id('field_productCategory'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setThumbnailImageUrlInput(thumbnailImageUrl) {
    await this.thumbnailImageUrlInput.sendKeys(thumbnailImageUrl);
  }

  async getThumbnailImageUrlInput() {
    return await this.thumbnailImageUrlInput.getAttribute('value');
  }

  async setOriginalImageUrlInput(originalImageUrl) {
    await this.originalImageUrlInput.sendKeys(originalImageUrl);
  }

  async getOriginalImageUrlInput() {
    return await this.originalImageUrlInput.getAttribute('value');
  }

  async setBannerTallImageUrlInput(bannerTallImageUrl) {
    await this.bannerTallImageUrlInput.sendKeys(bannerTallImageUrl);
  }

  async getBannerTallImageUrlInput() {
    return await this.bannerTallImageUrlInput.getAttribute('value');
  }

  async setBannerWideImageUrlInput(bannerWideImageUrl) {
    await this.bannerWideImageUrlInput.sendKeys(bannerWideImageUrl);
  }

  async getBannerWideImageUrlInput() {
    return await this.bannerWideImageUrlInput.getAttribute('value');
  }

  async setCircleImageUrlInput(circleImageUrl) {
    await this.circleImageUrlInput.sendKeys(circleImageUrl);
  }

  async getCircleImageUrlInput() {
    return await this.circleImageUrlInput.getAttribute('value');
  }

  async setSharpenedImageUrlInput(sharpenedImageUrl) {
    await this.sharpenedImageUrlInput.sendKeys(sharpenedImageUrl);
  }

  async getSharpenedImageUrlInput() {
    return await this.sharpenedImageUrlInput.getAttribute('value');
  }

  async setSquareImageUrlInput(squareImageUrl) {
    await this.squareImageUrlInput.sendKeys(squareImageUrl);
  }

  async getSquareImageUrlInput() {
    return await this.squareImageUrlInput.getAttribute('value');
  }

  async setWatermarkImageUrlInput(watermarkImageUrl) {
    await this.watermarkImageUrlInput.sendKeys(watermarkImageUrl);
  }

  async getWatermarkImageUrlInput() {
    return await this.watermarkImageUrlInput.getAttribute('value');
  }

  async setPriorityInput(priority) {
    await this.priorityInput.sendKeys(priority);
  }

  async getPriorityInput() {
    return await this.priorityInput.getAttribute('value');
  }

  getDefaultIndInput(timeout?: number) {
    return this.defaultIndInput;
  }

  async stockItemSelectLastOption(timeout?: number) {
    await this.stockItemSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async stockItemSelectOption(option) {
    await this.stockItemSelect.sendKeys(option);
  }

  getStockItemSelect(): ElementFinder {
    return this.stockItemSelect;
  }

  async getStockItemSelectedOption() {
    return await this.stockItemSelect.element(by.css('option:checked')).getText();
  }

  async productCategorySelectLastOption(timeout?: number) {
    await this.productCategorySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productCategorySelectOption(option) {
    await this.productCategorySelect.sendKeys(option);
  }

  getProductCategorySelect(): ElementFinder {
    return this.productCategorySelect;
  }

  async getProductCategorySelectedOption() {
    return await this.productCategorySelect.element(by.css('option:checked')).getText();
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

export class PhotosDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-photos-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-photos'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
