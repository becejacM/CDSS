import { ISymptom } from "./ISymptom";
import { DiseaseType } from "./DiseaseType";

export class IDisease{
    id:any;
    name:string;
    typeOfDisease:DiseaseType;
    symptoms:ISymptom[];
    syms:String;
}