export interface IProductDocument {
  id?: number;
  videoUrl?: string;
  highlightsUrl?: string;
  longDescriptionUrl?: string;
  shortDescriptionUrl?: string;
  descriptionUrl?: string;
  careInstructionsUrl?: string;
  specialFeaturesUrl?: string;
  usageAndSideEffectsUrl?: string;
  safetyWarnningUrl?: string;
  productType?: string;
  modelName?: string;
  modelNumber?: string;
  fabricType?: string;
  productComplianceCertificate?: string;
  genuineAndLegal?: boolean;
  countryOfOrigin?: string;
  warrantyPeriod?: string;
  warrantyPolicy?: string;
  warrantyTypeName?: string;
  warrantyTypeId?: number;
  cultureName?: string;
  cultureId?: number;
}

export class ProductDocument implements IProductDocument {
  constructor(
    public id?: number,
    public videoUrl?: string,
    public highlightsUrl?: string,
    public longDescriptionUrl?: string,
    public shortDescriptionUrl?: string,
    public descriptionUrl?: string,
    public careInstructionsUrl?: string,
    public specialFeaturesUrl?: string,
    public usageAndSideEffectsUrl?: string,
    public safetyWarnningUrl?: string,
    public productType?: string,
    public modelName?: string,
    public modelNumber?: string,
    public fabricType?: string,
    public productComplianceCertificate?: string,
    public genuineAndLegal?: boolean,
    public countryOfOrigin?: string,
    public warrantyPeriod?: string,
    public warrantyPolicy?: string,
    public warrantyTypeName?: string,
    public warrantyTypeId?: number,
    public cultureName?: string,
    public cultureId?: number
  ) {
    this.genuineAndLegal = this.genuineAndLegal || false;
  }
}
