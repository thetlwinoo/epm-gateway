// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { BlobDocumentComponentsPage, BlobDocumentDeleteDialog, BlobDocumentUpdatePage } from './blob-document.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('BlobDocument e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let blobDocumentComponentsPage: BlobDocumentComponentsPage;
  let blobDocumentUpdatePage: BlobDocumentUpdatePage;
  let blobDocumentDeleteDialog: BlobDocumentDeleteDialog;
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

  it('should load BlobDocuments', async () => {
    await navBarPage.goToEntity('blob-document');
    blobDocumentComponentsPage = new BlobDocumentComponentsPage();
    await browser.wait(ec.visibilityOf(blobDocumentComponentsPage.title), 5000);
    expect(await blobDocumentComponentsPage.getTitle()).to.eq('epmgatewayApp.epmcloudblobBlobDocument.home.title');
  });

  it('should load create BlobDocument page', async () => {
    await blobDocumentComponentsPage.clickOnCreateButton();
    blobDocumentUpdatePage = new BlobDocumentUpdatePage();
    expect(await blobDocumentUpdatePage.getPageTitle()).to.eq('epmgatewayApp.epmcloudblobBlobDocument.home.createOrEditLabel');
    await blobDocumentUpdatePage.cancel();
  });

  it('should create and save BlobDocuments', async () => {
    const nbButtonsBeforeCreate = await blobDocumentComponentsPage.countDeleteButtons();

    await blobDocumentComponentsPage.clickOnCreateButton();
    await promise.all([
      blobDocumentUpdatePage.setDocumentInput(absolutePath),
      blobDocumentUpdatePage.setLastEditedByInput('lastEditedBy'),
      blobDocumentUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
    ]);
    expect(await blobDocumentUpdatePage.getDocumentInput()).to.endsWith(
      fileNameToUpload,
      'Expected Document value to be end with ' + fileNameToUpload
    );
    expect(await blobDocumentUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await blobDocumentUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await blobDocumentUpdatePage.save();
    expect(await blobDocumentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await blobDocumentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last BlobDocument', async () => {
    const nbButtonsBeforeDelete = await blobDocumentComponentsPage.countDeleteButtons();
    await blobDocumentComponentsPage.clickOnLastDeleteButton();

    blobDocumentDeleteDialog = new BlobDocumentDeleteDialog();
    expect(await blobDocumentDeleteDialog.getDialogTitle()).to.eq('epmgatewayApp.epmcloudblobBlobDocument.delete.question');
    await blobDocumentDeleteDialog.clickOnConfirmButton();

    expect(await blobDocumentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
