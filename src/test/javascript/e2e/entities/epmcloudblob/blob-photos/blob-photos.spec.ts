// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { BlobPhotosComponentsPage, BlobPhotosDeleteDialog, BlobPhotosUpdatePage } from './blob-photos.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('BlobPhotos e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let blobPhotosComponentsPage: BlobPhotosComponentsPage;
  let blobPhotosUpdatePage: BlobPhotosUpdatePage;
  let blobPhotosDeleteDialog: BlobPhotosDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BlobPhotos', async () => {
    await navBarPage.goToEntity('blob-photos');
    blobPhotosComponentsPage = new BlobPhotosComponentsPage();
    await browser.wait(ec.visibilityOf(blobPhotosComponentsPage.title), 5000);
    expect(await blobPhotosComponentsPage.getTitle()).to.eq('epmgatewayApp.epmcloudblobBlobPhotos.home.title');
  });

  it('should load create BlobPhotos page', async () => {
    await blobPhotosComponentsPage.clickOnCreateButton();
    blobPhotosUpdatePage = new BlobPhotosUpdatePage();
    expect(await blobPhotosUpdatePage.getPageTitle()).to.eq('epmgatewayApp.epmcloudblobBlobPhotos.home.createOrEditLabel');
    await blobPhotosUpdatePage.cancel();
  });

  it('should create and save BlobPhotos', async () => {
    const nbButtonsBeforeCreate = await blobPhotosComponentsPage.countDeleteButtons();

    await blobPhotosComponentsPage.clickOnCreateButton();
    await promise.all([
      blobPhotosUpdatePage.setThumbnailPhotoBlobInput(absolutePath),
      blobPhotosUpdatePage.setOriginalPhotoBlobInput(absolutePath),
      blobPhotosUpdatePage.setBannerTallPhotoBlobInput(absolutePath),
      blobPhotosUpdatePage.setBannerWidePhotoBlobInput(absolutePath),
      blobPhotosUpdatePage.setCirclePhotoBlobInput(absolutePath),
      blobPhotosUpdatePage.setSharpenedPhotoBlobInput(absolutePath),
      blobPhotosUpdatePage.setSquarePhotoBlobInput(absolutePath),
      blobPhotosUpdatePage.setWatermarkPhotoBlobInput(absolutePath),
      blobPhotosUpdatePage.setLastEditedByInput('lastEditedBy'),
      blobPhotosUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
    ]);
    expect(await blobPhotosUpdatePage.getThumbnailPhotoBlobInput()).to.endsWith(
      fileNameToUpload,
      'Expected ThumbnailPhotoBlob value to be end with ' + fileNameToUpload
    );
    expect(await blobPhotosUpdatePage.getOriginalPhotoBlobInput()).to.endsWith(
      fileNameToUpload,
      'Expected OriginalPhotoBlob value to be end with ' + fileNameToUpload
    );
    expect(await blobPhotosUpdatePage.getBannerTallPhotoBlobInput()).to.endsWith(
      fileNameToUpload,
      'Expected BannerTallPhotoBlob value to be end with ' + fileNameToUpload
    );
    expect(await blobPhotosUpdatePage.getBannerWidePhotoBlobInput()).to.endsWith(
      fileNameToUpload,
      'Expected BannerWidePhotoBlob value to be end with ' + fileNameToUpload
    );
    expect(await blobPhotosUpdatePage.getCirclePhotoBlobInput()).to.endsWith(
      fileNameToUpload,
      'Expected CirclePhotoBlob value to be end with ' + fileNameToUpload
    );
    expect(await blobPhotosUpdatePage.getSharpenedPhotoBlobInput()).to.endsWith(
      fileNameToUpload,
      'Expected SharpenedPhotoBlob value to be end with ' + fileNameToUpload
    );
    expect(await blobPhotosUpdatePage.getSquarePhotoBlobInput()).to.endsWith(
      fileNameToUpload,
      'Expected SquarePhotoBlob value to be end with ' + fileNameToUpload
    );
    expect(await blobPhotosUpdatePage.getWatermarkPhotoBlobInput()).to.endsWith(
      fileNameToUpload,
      'Expected WatermarkPhotoBlob value to be end with ' + fileNameToUpload
    );
    expect(await blobPhotosUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await blobPhotosUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await blobPhotosUpdatePage.save();
    expect(await blobPhotosUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await blobPhotosComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last BlobPhotos', async () => {
    const nbButtonsBeforeDelete = await blobPhotosComponentsPage.countDeleteButtons();
    await blobPhotosComponentsPage.clickOnLastDeleteButton();

    blobPhotosDeleteDialog = new BlobPhotosDeleteDialog();
    expect(await blobPhotosDeleteDialog.getDialogTitle()).to.eq('epmgatewayApp.epmcloudblobBlobPhotos.delete.question');
    await blobPhotosDeleteDialog.clickOnConfirmButton();

    expect(await blobPhotosComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
