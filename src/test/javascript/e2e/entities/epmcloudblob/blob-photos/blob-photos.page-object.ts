import { element, by, ElementFinder } from 'protractor';

export class BlobPhotosComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-blob-photos div table .btn-danger'));
  title = element.all(by.css('jhi-blob-photos div h2#page-heading span')).first();

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

export class BlobPhotosUpdatePage {
  pageTitle = element(by.id('jhi-blob-photos-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  thumbnailPhotoBlobInput = element(by.id('file_thumbnailPhotoBlob'));
  originalPhotoBlobInput = element(by.id('file_originalPhotoBlob'));
  bannerTallPhotoBlobInput = element(by.id('file_bannerTallPhotoBlob'));
  bannerWidePhotoBlobInput = element(by.id('file_bannerWidePhotoBlob'));
  circlePhotoBlobInput = element(by.id('file_circlePhotoBlob'));
  sharpenedPhotoBlobInput = element(by.id('file_sharpenedPhotoBlob'));
  squarePhotoBlobInput = element(by.id('file_squarePhotoBlob'));
  watermarkPhotoBlobInput = element(by.id('file_watermarkPhotoBlob'));
  lastEditedByInput = element(by.id('field_lastEditedBy'));
  lastEditedWhenInput = element(by.id('field_lastEditedWhen'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setThumbnailPhotoBlobInput(thumbnailPhotoBlob) {
    await this.thumbnailPhotoBlobInput.sendKeys(thumbnailPhotoBlob);
  }

  async getThumbnailPhotoBlobInput() {
    return await this.thumbnailPhotoBlobInput.getAttribute('value');
  }

  async setOriginalPhotoBlobInput(originalPhotoBlob) {
    await this.originalPhotoBlobInput.sendKeys(originalPhotoBlob);
  }

  async getOriginalPhotoBlobInput() {
    return await this.originalPhotoBlobInput.getAttribute('value');
  }

  async setBannerTallPhotoBlobInput(bannerTallPhotoBlob) {
    await this.bannerTallPhotoBlobInput.sendKeys(bannerTallPhotoBlob);
  }

  async getBannerTallPhotoBlobInput() {
    return await this.bannerTallPhotoBlobInput.getAttribute('value');
  }

  async setBannerWidePhotoBlobInput(bannerWidePhotoBlob) {
    await this.bannerWidePhotoBlobInput.sendKeys(bannerWidePhotoBlob);
  }

  async getBannerWidePhotoBlobInput() {
    return await this.bannerWidePhotoBlobInput.getAttribute('value');
  }

  async setCirclePhotoBlobInput(circlePhotoBlob) {
    await this.circlePhotoBlobInput.sendKeys(circlePhotoBlob);
  }

  async getCirclePhotoBlobInput() {
    return await this.circlePhotoBlobInput.getAttribute('value');
  }

  async setSharpenedPhotoBlobInput(sharpenedPhotoBlob) {
    await this.sharpenedPhotoBlobInput.sendKeys(sharpenedPhotoBlob);
  }

  async getSharpenedPhotoBlobInput() {
    return await this.sharpenedPhotoBlobInput.getAttribute('value');
  }

  async setSquarePhotoBlobInput(squarePhotoBlob) {
    await this.squarePhotoBlobInput.sendKeys(squarePhotoBlob);
  }

  async getSquarePhotoBlobInput() {
    return await this.squarePhotoBlobInput.getAttribute('value');
  }

  async setWatermarkPhotoBlobInput(watermarkPhotoBlob) {
    await this.watermarkPhotoBlobInput.sendKeys(watermarkPhotoBlob);
  }

  async getWatermarkPhotoBlobInput() {
    return await this.watermarkPhotoBlobInput.getAttribute('value');
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

export class BlobPhotosDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-blobPhotos-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-blobPhotos'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
