import { ResultType, VerificationType } from '../common';

export interface SendVerificationReq {
  type: VerificationType;
  tel: string;
}

export interface SendVerificationResp {
  resultType: ResultType;
}
