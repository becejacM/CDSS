import { Component, OnInit } from '@angular/core';
import { PatientService } from '../../services/patient/patient.service';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http/src/response';
import { IPatient } from '../../model/IPatient';
import { DiseaseService } from '../../services/diseases/disease.service';
import { ISymptom } from '../../model/ISymptom';
import { IDisease } from '../../model/IDisease';
import { ListOfSymptoms } from '../../model/ListOfSymptoms';
import { IMedicine } from '../../model/IMedicine';
import { MedicineService } from '../../services/medicines/medicine.service';
import { ITherapy } from '../../model/ITherapy';
import { DiagnosticProccessService } from '../../services/diagnostic-proccess/diagnostic-proccess.service';
import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import { forEach } from '@angular/router/src/utils/collection';
import { ListOfMedicines } from '../../model/ListOfMedicines';
@Component({
  selector: 'app-diagnostic-process-form',
  templateUrl: './diagnostic-process-form.component.html',
  styleUrls: ['./diagnostic-process-form.component.css']
})
export class DiagnosticProcessFormComponent implements OnInit {

  
  id:any;
  addForm: FormGroup;
  addDForm: FormGroup;  
  chack: Boolean;
  active:String;
  diagnoseForm: FormGroup;
  validateForm:Boolean;
  message:Boolean;
  toggleListOfDisease:Boolean;
  toggleListOfSymptoms:Boolean;  
  enterSymptom:Boolean;
  enterDisease:Boolean;
  
  listOfMedicines: IMedicine[] = new Array;
  Medicine: IMedicine = new IMedicine;
  diseases: IDisease[];
  symptoms:ISymptom[];
  Therapy: ITherapy = new ITherapy;
  constructor(private router: Router, private toastr: ToastrService,
    private patientService: PatientService,private diseaseService: DiseaseService,
    private dpService: DiagnosticProccessService,
    private medicineService: MedicineService, private route: ActivatedRoute, private fb: FormBuilder) { }

  ngOnInit() {
    console.log("evo meee");
    this.route.params.subscribe(params => {
      this.id = +params['id'];
    });
    console.log(this.id);
    this.chack = false;
    this.toggleListOfDisease=false;
    this.enterSymptom=false;
    this.enterDisease=false;
    this.createForm();
    this.createDiagnoseForm();
    this.active="";
    this.validateForm = false;
    this.message = false;
    this.initializeWebSocketConnection();
  }
  createForm() {
    this.addForm = this.fb.group({
      sym: ['', Validators.compose([Validators.required])],
      symptoms: ['', Validators.compose([Validators.required])]

    });
  }

  createFormForDisease() {
    this.addDForm = this.fb.group({
      disease: ['', Validators.compose([Validators.required])]

    });
  }
  createDiagnoseForm() {
    this.listOfMedicines = new Array;
    this.Medicine = new IMedicine;
    this.diagnoseForm = this.fb.group({
      diseasename: ['', Validators.compose([Validators.required])],
      medicines: ['', Validators.compose([Validators.required])],
      med: ['', Validators.compose([Validators.required])],
      message: ['', Validators.compose([Validators.required])],
    });
  }
  
  createNewDF() {
    this.listOfMedicines = new Array;
    this.Medicine = new IMedicine;
    console.log("cistim");
    this.diagnoseForm = this.fb.group({
      diseasename: [this.therapy.diseasename, Validators.compose([Validators.required])],
      medicines: ['', Validators.compose([Validators.required])],
      med: ['', Validators.compose([Validators.required])],
      message: [this.therapy.message, Validators.compose([Validators.required])],
    });
  }
  listOfSymptoms: ISymptom[] = new Array;
  Symptom: ISymptom = new ISymptom;
  Disease: IDisease = new IDisease;
  list : ListOfSymptoms = new ListOfSymptoms;
  listM : ListOfMedicines = new ListOfMedicines;
  checkSymptom() {
    this.chack = true;
    if (this.addForm.value['sym'] != "") {
      console.log(this.addForm.value['sym']);
      this.Symptom = new ISymptom;
      this.Symptom.name = this.addForm.value['sym'];
      this.diseaseService.checkSymptom(this.Symptom.name)
        .subscribe(data => {
          console.log(data);
          let newValue =this.addForm.value["symptoms"] + " " + this.Symptom.name;
          this.addForm.controls["symptoms"].setValue(newValue);
          this.listOfSymptoms.push(this.Symptom);
          this.list.symptoms.push(this.Symptom.name);
          this.chack = false;
        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message);
          } else {
            this.toastr.error(err.error.message);
          }
        });
    }
  }

  
  checkMedicine(){
    this.chack = true;
    if (this.diagnoseForm.value['med'] != "") {
      console.log(this.diagnoseForm.value['med']);
      this.Medicine = new IMedicine;
      this.Medicine.name = this.diagnoseForm.value['med'];
      console.log(this.Medicine.name);
      this.medicineService.checkMedicine(this.Medicine.name)
        .subscribe(data => {
          console.log(data);
          let b = false;
          
            let newValue =this.diagnoseForm.value["medicines"] + " " + this.Medicine.name+"\n";
            this.diagnoseForm.controls["medicines"].setValue(newValue);
            this.listOfMedicines.push(this.Medicine);
            this.listM.medicines.push(this.Medicine.name);
          this.chack = false;
        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
    }
  }
  submit() {
  }

  disease(){
    this.list.patientid=this.id;
    console.log(this.list);
    this.diseaseService.getDiagnose(this.list)
    .subscribe(data => {
        console.log(data);
        this.list=new ListOfSymptoms;
        this.createForm();
        this.therapy.diseasename = data.diseasename;    
        this.therapy.medicines = data.medicines;                
        this.therapy.symptoms = data.symptoms;
        this.therapy.patientId = data.patientId;                
        this.therapy.message="";
        this.active = "diagnose";
        this.createNewDF();
      },
      (err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        } else {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        }
      });
  }

  listdisease(){
    this.toggleListOfDisease = !this.toggleListOfDisease;
    if(this.toggleListOfDisease){
      this.getDiseases();
    }
  }
  eSymptom(){
    this.enterSymptom = !this.enterSymptom;
    if(this.enterSymptom){
      this.createForm();
    }
    this.active="";
    this.toggleListOfSymptoms=false;
    this.enterDisease=false;
  }

  eDisease(){
    this.enterDisease = !this.enterDisease;
    if(this.enterDisease){
      this.createFormForDisease();
    }
    this.active="";
    this.toggleListOfDisease=false;
    this.enterSymptom=false;
    
  }

  listsymptom(){
    console.log(this.addDForm.value['disease']);
    this.diseaseService.getSortedSymptoms(this.addDForm.value['disease'])
    .subscribe(data => {
      this.list=new ListOfSymptoms;        
      this.symptoms = data;
      this.toggleListOfSymptoms=true;
      this.createDiagnoseForm();
    },
    (err: HttpErrorResponse) => {
      if (err.error instanceof Error) {
        this.toastr.error(err.error.message + '\nError Status ' + err.status);
      } else {
        this.toastr.error(err.error.message + '\nError Status ' + err.status);
      }
    });
  }
  getDiseases() {
    this.diseaseService.getSortedDiseases(this.list)
      .subscribe(data => {
        this.list=new ListOfSymptoms;        
        this.diseases = data;
        this.createForm();
        this.createNewDF();        
      },
      (err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        } else {
          this.toastr.error(err.error.message + '\nError Status ' + err.status);
        }
      });
  }

  submitDisease(){
    
  }
  diagnose(){
    if(this.active==="diagnose"){
      this.active="";
    }
    else{
      this.active="diagnose";      
    }
    console.log(this.list);
    this.toggleListOfSymptoms=false;
  }
  therapy:ITherapy=new ITherapy;
  
  submitDiagnose(){
    if(!this.chack){
      console.log("dijagnoza");
      this.dpService.add(this.therapy)
      .subscribe(data => {
          console.log(data);
          this.validateForm=false;
          this.active="";
          this.therapy = new ITherapy;
          this.createForm();
          this.createDiagnoseForm();
        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
    }
  }
  message1:string;
  message2:string;
  validate(){
    this.chack=true;
    console.log(this.id);
      this.therapy.diseasename = this.diagnoseForm.value['diseasename'];
      this.therapy.message = this.diagnoseForm.value['message'];      
      this.therapy.medicines=this.listOfMedicines;
      this.therapy.medicines.forEach(element => {
        console.log("evo meeee: "+element);
        
      });
      this.message1="";
      this.message2="";
      //this.therapy.symptoms=this.listOfSymptoms;
      this.therapy.patientId=this.id;
      this.dpService.validate(this.therapy)
      .subscribe(data => {
          console.log(data);
          this.chack=false;
          if(!this.message){
            this.validateForm=true;
          }
          else{
            this.message = false;
          }
          //this.validateForm=true;
        },
        (err: HttpErrorResponse) => {
          if (err.error instanceof Error) {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          } else {
            this.toastr.error(err.error.message + '\nError Status ' + err.status);
          }
        });
  }

  private stompClient;
  ws:any;
  disabled: boolean;
  
  initializeWebSocketConnection(){
    if (this.ws != null) {
      console.log("disconnect");
      this.ws.ws.close();
    }
    //let ws = new SockJS(http://localhost:8080/greeting);
    //var socket = new SockJS('wss://localhost:8443/socket');
    //this.ws = Stomp.over(socket);
    console.log("connect");
    console.log(Stomp);
    let socket = new WebSocket("ws://localhost:8080/secured/sbnz");
    this.ws = Stomp.over(socket);
    let that = this;
    //this.sendName();
    this.ws.connect({}, function(frame) {
      
      that.ws.subscribe("/secured/alergies", function(message) {
        console.log(message); 
        if(that.message1===""){
          that.message1=message['body'];
        }    
        else if(that.message2==="" && that.message2!==message['body']){
          that.message2=message['body'];
        }
        if(that.message1!="" && that.message2!="")    {
          that.validateForm=false;
          that.message = true;
          that.toastr.warning(that.message1);
          that.toastr.warning(that.message2);          
          that.createNewDF();
          that.message1="";
          that.message2="";
        }
        
      });
    }, function(error) {
      console.log("STOMP error " + error);
    });
  }
}
