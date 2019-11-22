// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { PhotosComponentsPage, PhotosDeleteDialog, PhotosUpdatePage } from './photos.page-object';

const expect = chai.expect;

describe('Photos e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let photosComponentsPage: PhotosComponentsPage;
  let photosUpdatePage: PhotosUpdatePage;
  let photosDeleteDialog: PhotosDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Photos', async () => {
    await navBarPage.goToEntity('photos');
    photosComponentsPage = new PhotosComponentsPage();
    await browser.wait(ec.visibilityOf(photosComponentsPage.title), 5000);
    expect(await photosComponentsPage.getTitle()).to.eq('epmgatewayApp.photos.home.title');
  });

  it('should load create Photos page', async () => {
    await photosComponentsPage.clickOnCreateButton();
    photosUpdatePage = new PhotosUpdatePage();
    expect(await photosUpdatePage.getPageTitle()).to.eq('epmgatewayApp.photos.home.createOrEditLabel');
    await photosUpdatePage.cancel();
  });

  it('should create and save Photos', async () => {
    const nbButtonsBeforeCreate = await photosComponentsPage.countDeleteButtons();

    await photosComponentsPage.clickOnCreateButton();
    await promise.all([
      photosUpdatePage.setThumbnailImageUrlInput('thumbnailImageUrl'),
      photosUpdatePage.setOriginalImageUrlInput('originalImageUrl'),
      photosUpdatePage.setBannerTallImageUrlInput('bannerTallImageUrl'),
      photosUpdatePage.setBannerWideImageUrlInput('bannerWideImageUrl'),
      photosUpdatePage.setCircleImageUrlInput('circleImageUrl'),
      photosUpdatePage.setSharpenedImageUrlInput('sharpenedImageUrl'),
      photosUpdatePage.setSquareImageUrlInput('squareImageUrl'),
      photosUpdatePage.setWatermarkImageUrlInput('watermarkImageUrl'),
      photosUpdatePage.setPriorityInput('5'),
      photosUpdatePage.stockItemSelectLastOption(),
      photosUpdatePage.productCategorySelectLastOption()
    ]);
    expect(await photosUpdatePage.getThumbnailImageUrlInput()).to.eq(
      'thumbnailImageUrl',
      'Expected ThumbnailImageUrl value to be equals to thumbnailImageUrl'
    );
    expect(await photosUpdatePage.getOriginalImageUrlInput()).to.eq(
      'originalImageUrl',
      'Expected OriginalImageUrl value to be equals to originalImageUrl'
    );
    expect(await photosUpdatePage.getBannerTallImageUrlInput()).to.eq(
      'bannerTallImageUrl',
      'Expected BannerTallImageUrl value to be equals to bannerTallImageUrl'
    );
    expect(await photosUpdatePage.getBannerWideImageUrlInput()).to.eq(
      'bannerWideImageUrl',
      'Expected BannerWideImageUrl value to be equals to bannerWideImageUrl'
    );
    expect(await photosUpdatePage.getCircleImageUrlInput()).to.eq(
      'circleImageUrl',
      'Expected CircleImageUrl value to be equals to circleImageUrl'
    );
    expect(await photosUpdatePage.getSharpenedImageUrlInput()).to.eq(
      'sharpenedImageUrl',
      'Expected SharpenedImageUrl value to be equals to sharpenedImageUrl'
    );
    expect(await photosUpdatePage.getSquareImageUrlInput()).to.eq(
      'squareImageUrl',
      'Expected SquareImageUrl value to be equals to squareImageUrl'
    );
    expect(await photosUpdatePage.getWatermarkImageUrlInput()).to.eq(
      'watermarkImageUrl',
      'Expected WatermarkImageUrl value to be equals to watermarkImageUrl'
    );
    expect(await photosUpdatePage.getPriorityInput()).to.eq('5', 'Expected priority value to be equals to 5');
    const selectedDefaultInd = photosUpdatePage.getDefaultIndInput();
    if (await selectedDefaultInd.isSelected()) {
      await photosUpdatePage.getDefaultIndInput().click();
      expect(await photosUpdatePage.getDefaultIndInput().isSelected(), 'Expected defaultInd not to be selected').to.be.false;
    } else {
      await photosUpdatePage.getDefaultIndInput().click();
      expect(await photosUpdatePage.getDefaultIndInput().isSelected(), 'Expected defaultInd to be selected').to.be.true;
    }
    await photosUpdatePage.save();
    expect(await photosUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await photosComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Photos', async () => {
    const nbButtonsBeforeDelete = await photosComponentsPage.countDeleteButtons();
    await photosComponentsPage.clickOnLastDeleteButton();

    photosDeleteDialog = new PhotosDeleteDialog();
    expect(await photosDeleteDialog.getDialogTitle()).to.eq('epmgatewayApp.photos.delete.question');
    await photosDeleteDialog.clickOnConfirmButton();

    expect(await photosComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
