import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AppointmentServiceService } from 'src/app/http-services/appointment-service.service';

@Component({
  selector: 'app-app-qr-scan',
  templateUrl: './app-qr-scan.component.html',
  styleUrls: ['./app-qr-scan.component.css']
})
export class AppQrScanComponent {

  qrCodeImage : File | null = null;
  //used just for displaying image
  imageUrl : string | ArrayBuffer | null = null
  cameraOn: boolean = false;
  
  get uploadDisabled()
  {
    if(this.imageUrl)
    {
      return false;
    }
    return true;
  }

  constructor(private readonly http : HttpClient,
              private readonly router : Router,
              private readonly appointmentService : AppointmentServiceService){}

  onFileChanged = (event : Event) : void =>
  {
    if(this.cameraOn){
      this.turnOffCamera();
    }

    const inputElement = event.target as HTMLInputElement;
    if(inputElement.files){
     //Loading image from <input>
     this.qrCodeImage = inputElement.files[0];
    }

    //Just showing uploaded image
    if(!inputElement.files) return;
    let file :Blob = inputElement.files[0];
    this.showImage(file);
  }


  goToAppointment = () => {
    if(!this.qrCodeImage) return;

    this.appointmentService.scanQrCode(this.qrCodeImage).subscribe({
      next: response =>
      {
          this.router.navigate(['staff/appointment-details/' + response.appointmentId + '/' + response.bloodDonorId]);
      },
      //TODO beautify messages
      error: (errResponse : HttpErrorResponse) =>
      {
        alert(errResponse.error);
      }

    })
  }

toggleCamera =  () => {
  if(this.cameraOn)
  {
    this.turnOffCamera();
  }
  else
  {
    this.turnOnCamera();
  }
}

private turnOnCamera = () =>{
  const video = document.querySelector('#webcam') as HTMLMediaElement;

  //Gets video stream for web camera and puts it as source for <video>
  navigator.mediaDevices.getUserMedia({ video: true })
  .then(stream => {
      video.srcObject = stream;
  });

  this.cameraOn = true;
}

private turnOffCamera = () =>{
    const video = document.querySelector('#webcam') as HTMLMediaElement;

    const stream = video.srcObject as MediaStream;

    //Stops all streams in <video>
    const tracks = stream.getTracks();
    tracks.forEach(track => track.stop());
    video.srcObject = null;

    this.cameraOn = false;
}


captureImage = () => {

    const video = document.querySelector('#webcam') as HTMLMediaElement;
    //Canvas is hidden because it is just used to capture image
    const canvas = document.querySelector('#canvas') as HTMLCanvasElement;
    
    //Screenshots video
    const ctx = canvas.getContext('2d');
    canvas.width = 400;
    canvas.height = 400;
    ctx?.drawImage(video as CanvasImageSource, 0, 0, canvas.width, canvas.height);
    
    // get the image data from the canvas
    const canvasURI = canvas.toDataURL();
    const imageBlob : Blob = this.dataURItoBlob(canvasURI);
    this.qrCodeImage = new File([imageBlob], 'qrCode')

    //Shows captured image
    this.showImage(this.qrCodeImage);
    this.toggleCamera();
  }



  private showImage = (file : Blob):void =>{

    let reader : FileReader = new FileReader();
    reader.readAsDataURL(file);
      reader.onload = () => {
        this.imageUrl = reader.result; 
      };
  }


  private dataURItoBlob = (dataURI: string) => {
      // convert base64/URLEncoded data component to raw binary data held in a string
      var byteString;
      if (dataURI.split(',')[0].indexOf('base64') >= 0)
        byteString = atob(dataURI.split(',')[1]);
      else
        byteString = unescape(dataURI.split(',')[1]);
      // separate out the mime component
      var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
      // write the bytes of the string to a typed array
      var ia = new Uint8Array(byteString.length);
      for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
      }
      return new Blob([ia], { type: mimeString });
  }

}
