// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { ProductDocumentComponentsPage, ProductDocumentDeleteDialog, ProductDocumentUpdatePage } from './product-document.page-object';

const expect = chai.expect;

describe('ProductDocument e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productDocumentComponentsPage: ProductDocumentComponentsPage;
  let productDocumentUpdatePage: ProductDocumentUpdatePage;
  let productDocumentDeleteDialog: ProductDocumentDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductDocuments', async () => {
    await navBarPage.goToEntity('product-document');
    productDocumentComponentsPage = new ProductDocumentComponentsPage();
    await browser.wait(ec.visibilityOf(productDocumentComponentsPage.title), 5000);
    expect(await productDocumentComponentsPage.getTitle()).to.eq('epmgatewayApp.productDocument.home.title');
  });

  it('should load create ProductDocument page', async () => {
    await productDocumentComponentsPage.clickOnCreateButton();
    productDocumentUpdatePage = new ProductDocumentUpdatePage();
    expect(await productDocumentUpdatePage.getPageTitle()).to.eq('epmgatewayApp.productDocument.home.createOrEditLabel');
    await productDocumentUpdatePage.cancel();
  });

  it('should create and save ProductDocuments', async () => {
    const nbButtonsBeforeCreate = await productDocumentComponentsPage.countDeleteButtons();

    await productDocumentComponentsPage.clickOnCreateButton();
    await promise.all([
      productDocumentUpdatePage.setVideoUrlInput('videoUrl'),
      productDocumentUpdatePage.setHighlightsUrlInput('highlightsUrl'),
      productDocumentUpdatePage.setLongDescriptionUrlInput('longDescriptionUrl'),
      productDocumentUpdatePage.setShortDescriptionUrlInput('shortDescriptionUrl'),
      productDocumentUpdatePage.setDescriptionUrlInput('descriptionUrl'),
      productDocumentUpdatePage.setCareInstructionsUrlInput('careInstructionsUrl'),
      productDocumentUpdatePage.setSpecialFeaturesUrlInput('specialFeaturesUrl'),
      productDocumentUpdatePage.setUsageAndSideEffectsUrlInput('usageAndSideEffectsUrl'),
      productDocumentUpdatePage.setSafetyWarnningUrlInput('safetyWarnningUrl'),
      productDocumentUpdatePage.setProductTypeInput('productType'),
      productDocumentUpdatePage.setModelNameInput('modelName'),
      productDocumentUpdatePage.setModelNumberInput('modelNumber'),
      productDocumentUpdatePage.setFabricTypeInput('fabricType'),
      productDocumentUpdatePage.setProductComplianceCertificateInput('productComplianceCertificate'),
      productDocumentUpdatePage.setCountryOfOriginInput('countryOfOrigin'),
      productDocumentUpdatePage.setWarrantyPeriodInput('warrantyPeriod'),
      productDocumentUpdatePage.setWarrantyPolicyInput('warrantyPolicy'),
      productDocumentUpdatePage.warrantyTypeSelectLastOption(),
      productDocumentUpdatePage.cultureSelectLastOption()
    ]);
    expect(await productDocumentUpdatePage.getVideoUrlInput()).to.eq('videoUrl', 'Expected VideoUrl value to be equals to videoUrl');
    expect(await productDocumentUpdatePage.getHighlightsUrlInput()).to.eq(
      'highlightsUrl',
      'Expected HighlightsUrl value to be equals to highlightsUrl'
    );
    expect(await productDocumentUpdatePage.getLongDescriptionUrlInput()).to.eq(
      'longDescriptionUrl',
      'Expected LongDescriptionUrl value to be equals to longDescriptionUrl'
    );
    expect(await productDocumentUpdatePage.getShortDescriptionUrlInput()).to.eq(
      'shortDescriptionUrl',
      'Expected ShortDescriptionUrl value to be equals to shortDescriptionUrl'
    );
    expect(await productDocumentUpdatePage.getDescriptionUrlInput()).to.eq(
      'descriptionUrl',
      'Expected DescriptionUrl value to be equals to descriptionUrl'
    );
    expect(await productDocumentUpdatePage.getCareInstructionsUrlInput()).to.eq(
      'careInstructionsUrl',
      'Expected CareInstructionsUrl value to be equals to careInstructionsUrl'
    );
    expect(await productDocumentUpdatePage.getSpecialFeaturesUrlInput()).to.eq(
      'specialFeaturesUrl',
      'Expected SpecialFeaturesUrl value to be equals to specialFeaturesUrl'
    );
    expect(await productDocumentUpdatePage.getUsageAndSideEffectsUrlInput()).to.eq(
      'usageAndSideEffectsUrl',
      'Expected UsageAndSideEffectsUrl value to be equals to usageAndSideEffectsUrl'
    );
    expect(await productDocumentUpdatePage.getSafetyWarnningUrlInput()).to.eq(
      'safetyWarnningUrl',
      'Expected SafetyWarnningUrl value to be equals to safetyWarnningUrl'
    );
    expect(await productDocumentUpdatePage.getProductTypeInput()).to.eq(
      'productType',
      'Expected ProductType value to be equals to productType'
    );
    expect(await productDocumentUpdatePage.getModelNameInput()).to.eq('modelName', 'Expected ModelName value to be equals to modelName');
    expect(await productDocumentUpdatePage.getModelNumberInput()).to.eq(
      'modelNumber',
      'Expected ModelNumber value to be equals to modelNumber'
    );
    expect(await productDocumentUpdatePage.getFabricTypeInput()).to.eq(
      'fabricType',
      'Expected FabricType value to be equals to fabricType'
    );
    expect(await productDocumentUpdatePage.getProductComplianceCertificateInput()).to.eq(
      'productComplianceCertificate',
      'Expected ProductComplianceCertificate value to be equals to productComplianceCertificate'
    );
    const selectedGenuineAndLegal = productDocumentUpdatePage.getGenuineAndLegalInput();
    if (await selectedGenuineAndLegal.isSelected()) {
      await productDocumentUpdatePage.getGenuineAndLegalInput().click();
      expect(await productDocumentUpdatePage.getGenuineAndLegalInput().isSelected(), 'Expected genuineAndLegal not to be selected').to.be
        .false;
    } else {
      await productDocumentUpdatePage.getGenuineAndLegalInput().click();
      expect(await productDocumentUpdatePage.getGenuineAndLegalInput().isSelected(), 'Expected genuineAndLegal to be selected').to.be.true;
    }
    expect(await productDocumentUpdatePage.getCountryOfOriginInput()).to.eq(
      'countryOfOrigin',
      'Expected CountryOfOrigin value to be equals to countryOfOrigin'
    );
    expect(await productDocumentUpdatePage.getWarrantyPeriodInput()).to.eq(
      'warrantyPeriod',
      'Expected WarrantyPeriod value to be equals to warrantyPeriod'
    );
    expect(await productDocumentUpdatePage.getWarrantyPolicyInput()).to.eq(
      'warrantyPolicy',
      'Expected WarrantyPolicy value to be equals to warrantyPolicy'
    );
    await productDocumentUpdatePage.save();
    expect(await productDocumentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productDocumentComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProductDocument', async () => {
    const nbButtonsBeforeDelete = await productDocumentComponentsPage.countDeleteButtons();
    await productDocumentComponentsPage.clickOnLastDeleteButton();

    productDocumentDeleteDialog = new ProductDocumentDeleteDialog();
    expect(await productDocumentDeleteDialog.getDialogTitle()).to.eq('epmgatewayApp.productDocument.delete.question');
    await productDocumentDeleteDialog.clickOnConfirmButton();

    expect(await productDocumentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
