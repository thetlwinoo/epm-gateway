// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {
  BlobMixedDocumentComponentsPage,
  BlobMixedDocumentDeleteDialog,
  BlobMixedDocumentUpdatePage
} from './blob-mixed-document.page-object';

const expect = chai.expect;

describe('BlobMixedDocument e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let blobMixedDocumentComponentsPage: BlobMixedDocumentComponentsPage;
  let blobMixedDocumentUpdatePage: BlobMixedDocumentUpdatePage;
  let blobMixedDocumentDeleteDialog: BlobMixedDocumentDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BlobMixedDocuments', async () => {
    await navBarPage.goToEntity('blob-mixed-document');
    blobMixedDocumentComponentsPage = new BlobMixedDocumentComponentsPage();
    await browser.wait(ec.visibilityOf(blobMixedDocumentComponentsPage.title), 5000);
    expect(await blobMixedDocumentComponentsPage.getTitle()).to.eq('epmgatewayApp.epmcloudblobBlobMixedDocument.home.title');
  });

  it('should load create BlobMixedDocument page', async () => {
    await blobMixedDocumentComponentsPage.clickOnCreateButton();
    blobMixedDocumentUpdatePage = new BlobMixedDocumentUpdatePage();
    expect(await blobMixedDocumentUpdatePage.getPageTitle()).to.eq('epmgatewayApp.epmcloudblobBlobMixedDocument.home.createOrEditLabel');
    await blobMixedDocumentUpdatePage.cancel();
  });

  it('should create and save BlobMixedDocuments', async () => {
    const nbButtonsBeforeCreate = await blobMixedDocumentComponentsPage.countDeleteButtons();

    await blobMixedDocumentComponentsPage.clickOnCreateButton();
    await promise.all([
      blobMixedDocumentUpdatePage.setDocumentInput('document'),
      blobMixedDocumentUpdatePage.setLastEditedByInput('lastEditedBy'),
      blobMixedDocumentUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
    ]);
    expect(await blobMixedDocumentUpdatePage.getDocumentInput()).to.eq('document', 'Expected Document value to be equals to document');
    expect(await blobMixedDocumentUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await blobMixedDocumentUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await blobMixedDocumentUpdatePage.save();
    expect(await blobMixedDocumentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await blobMixedDocumentComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last BlobMixedDocument', async () => {
    const nbButtonsBeforeDelete = await blobMixedDocumentComponentsPage.countDeleteButtons();
    await blobMixedDocumentComponentsPage.clickOnLastDeleteButton();

    blobMixedDocumentDeleteDialog = new BlobMixedDocumentDeleteDialog();
    expect(await blobMixedDocumentDeleteDialog.getDialogTitle()).to.eq('epmgatewayApp.epmcloudblobBlobMixedDocument.delete.question');
    await blobMixedDocumentDeleteDialog.clickOnConfirmButton();

    expect(await blobMixedDocumentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
