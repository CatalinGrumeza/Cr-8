/**
 * @file: navbar.js
 * @author: CR-8
 * This code includes the logic to allow navigation in the carousel
 */

// carousel-laboratori
const trackLaboratori = document.querySelector(".carousel-track-laboratori");
const prevButtonLaboratori = document.querySelector(
  ".carousel-prev-laboratori"
);
const nextButtonLaboratori = document.querySelector(
  ".carousel-next-laboratori"
);
const cardsLaboratori = Array.from(trackLaboratori.children);
const cardWidthLaboratori = cardsLaboratori[0].getBoundingClientRect().width;

let currentLaboratoriIndex = 0;

function updateLaboratoriTrackPosition() {
  const amountToMove = -(cardWidthLaboratori + 20) * currentLaboratoriIndex;
  trackLaboratori.style.transition = "transform 0.5s ease-in-out";
  trackLaboratori.style.transform = `translateX(${amountToMove}px)`;
}

nextButtonLaboratori.addEventListener("click", () => {
  if (currentLaboratoriIndex < cardsLaboratori.length - 1) {
    currentLaboratoriIndex++;
  } else {
    currentLaboratoriIndex = 0;
  }
  updateLaboratoriTrackPosition();
});

prevButtonLaboratori.addEventListener("click", () => {
  if (currentLaboratoriIndex > 0) {
    currentLaboratoriIndex--;
  } else {
    currentLaboratoriIndex = cardsLaboratori.length - 1;
  }
  updateLaboratoriTrackPosition();
});

// carousel-image
const trackImage = document.querySelector(".carousel-track-image");
const prevButtonImage = document.querySelector(".carousel-prev-image");
const nextButtonImage = document.querySelector(".carousel-next-image");
const cardsImage = Array.from(trackImage.children);
const cardWidthImage = cardsImage[0].getBoundingClientRect().width;

let currentImageIndex = 0;

function updateImageTrackPosition() {
  const amountToMove = -(cardWidthImage + 20) * currentImageIndex;
  trackImage.style.transition = "transform 0.5s ease-in-out";
  trackImage.style.transform = `translateX(${amountToMove}px)`;
}

nextButtonImage.addEventListener("click", () => {
  if (currentImageIndex < cardsImage.length - 1) {
    currentImageIndex++;
  } else {
    currentImageIndex = 0;
  }
  updateImageTrackPosition();
});

prevButtonImage.addEventListener("click", () => {
  if (currentImageIndex > 0) {
    currentImageIndex--;
  } else {
    currentImageIndex = cardsImage.length - 1;
  }
  updateImageTrackPosition();
});
