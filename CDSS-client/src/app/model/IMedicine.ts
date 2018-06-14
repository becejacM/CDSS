import { IIngredient } from "./IIngredient";
import { MedicineType } from "./MedicineType";

export class IMedicine{
    name:String;
    typeOfMedicine:MedicineType;
    mi:IIngredient[];
    ingredients:String;
}