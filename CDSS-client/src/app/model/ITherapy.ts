import { ISymptom } from "./ISymptom";
import { IMedicine } from "./IMedicine";

export class ITherapy{
    patientId:Number;
    symptoms:ISymptom[];
    medicines:IMedicine[];
    diseasename:String;
    message:String;
}