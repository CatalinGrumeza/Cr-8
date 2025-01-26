/**
 * @file: main.js
 * @author: CR-8
 * This code includes the logic for the index.html page to manage booking form validation, info form validation,
 * labs carousel and anchor link animations
 */

/* ------------------------------ GENERIC FORM VALIDATION ------------------------------ */
const formValidation = (formId, fieldsConfig, apiEndpoint, successMessage) => {
  const form = document.getElementById(formId);

  // Validazione dei campi del form
  const validateField = (field, fieldConfig, errorSpan) => {
    let isValid = true;
    if (fieldConfig.regex) {
      isValid = fieldConfig.regex.test(field.value);
    }
    if (fieldConfig.customValidation) {
      isValid = isValid && fieldConfig.customValidation(field.value);
    }
    if (!isValid) {
      field.style.borderColor = "red";
      errorSpan.textContent = fieldConfig.error;
      errorSpan.style.color = "red";
    } else {
      field.style.borderColor = "green";
      errorSpan.textContent = "";
    }
    return isValid;
  };

  // Funzione per validare tutti i campi
  const validateFormFields = () => {
    let formIsValid = true;
    Object.keys(fieldsConfig).forEach((fieldId) => {
      const field = document.getElementById(fieldId);
      const errorSpan = document.getElementById(`${fieldId}Error`);
      if (!validateField(field, fieldsConfig[fieldId], errorSpan)) {
        formIsValid = false;
      }
    });
    return formIsValid;
  };

  // Aggiungi event listeners per la validazione dei campi
  Object.keys(fieldsConfig).forEach((fieldId) => {
    const field = document.getElementById(fieldId);
    const errorSpan = document.getElementById(`${fieldId}Error`);
    field.addEventListener("input", () =>
      validateField(field, fieldsConfig[fieldId], errorSpan)
    );
    field.addEventListener("blur", () =>
      validateField(field, fieldsConfig[fieldId], errorSpan)
    );
  });

  // Funzione per raccogliere i dati dal form e trasformarli
  const gatherFormData = () => {
    const data = {};
    const currentDate = new Date().toISOString().split("T")[0];

    if (formId === "bookingForm") {
      // Trasformazione dati per il booking form
      data.name = document.getElementById("nome").value;
      data.surname = document.getElementById("cognome").value;
      data.phone = document.getElementById("cellulare").value;
      data.email = document.getElementById("email").value;
      data.numberOfDays = parseInt(
        document.getElementById("numeroGiorni").value,
        10
      );
      data.additionalDetails =
        document.getElementById("richiesteParticolari").value || "";
      data.dataFrom = document.getElementById("disponibilitaDa").value;
      data.dataTo = document.getElementById("disponibilitaA").value;
      data.participantNumber = parseInt(
        document.getElementById("numeroPartecipanti").value,
        10
      );
      data.bookType = document.getElementById("periodoGiornata").value || "";
      data.visitorType = document.getElementById("visitatore").value;

      // Raccolta dei laboratori selezionati
      data.labs = Array.from(
        document.querySelectorAll(
          '#selectLaboratori input[type="checkbox"]:checked'
        )
      ).map((checkbox) => checkbox.value);

      data.createdAt = currentDate;
    } else if (formId === "infoForm") {
      // Trasformazione dati per il info form
      data.name = document.getElementById("nomeInfo").value;
      data.surname = document.getElementById("cognomeInfo").value;
      data.phone = document.getElementById("cellulareInfo").value;
      data.email = document.getElementById("emailInfo").value;
      data.text = document.getElementById("messaggio").value;
    }

    return data;
  };

  // Gestione dell'invio del form
  form.addEventListener("submit", async (event) => {
    event.preventDefault();
    const formIsValid = validateFormFields();

    const formErrorSpan = document.getElementById(`${formId}Error`);
    if (!formIsValid) {
      formErrorSpan.textContent =
        "Per favore, correggi gli errori evidenziati prima di inviare il modulo.";
      formErrorSpan.style.color = "red";
      return;
    }

    formErrorSpan.textContent = "";
    const data = gatherFormData();

    try {
      const response = await fetch(apiEndpoint, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      if (response.ok) {
        alert(successMessage);
        form.reset();
      } else {
        const errorData = await response.json();
        formErrorSpan.textContent = `Errore durante l'invio: ${
          errorData.message || "riprovare più tardi."
        }`;
        formErrorSpan.style.color = "red";
      }
    } catch (error) {
      formErrorSpan.textContent =
        "Si è verificato un errore. Per favore, riprova più tardi.";
      formErrorSpan.style.color = "red";
      console.error("Errore:", error);
    }
  });
};

/* ------------------------------ BOOKING FORM ------------------------------ */
const bookingFields = {
  nome: {
    regex: /^[a-zA-ZÀ-ÖØ-öø-ÿ\'\-]{2,}$/,
    error:
      "Il nome deve contenere almeno due lettere. Sono ammessi lettere, accenti e apostrofi.",
  },
  cognome: {
    regex: /^[a-zA-ZÀ-ÖØ-öø-ÿ\'\-]{2,}$/,
    error:
      "Il cognome deve contenere almeno due lettere. Sono ammessi lettere, accenti e apostrofi.",
  },
  cellulare: {
    regex: /^(\+39\s?)?\d{3}[-\s]?\d{3}[-\s]?\d{4}$/,
    error:
      "Il numero di telefono deve contenere 10 cifre separate da spazi o trattini. Il prefisso +39 è opzionale.",
  },
  email: {
    regex: /^[^@\s]+@[^@\s]+\.[^@\s]+$/,
    error: "Inserisci un'email valida.",
  },
  disponibilitaDa: {
    customValidation: (value) => new Date(value) >= new Date(),
    error: "La data deve essere successiva a oggi.",
  },
  disponibilitaA: {
    customValidation: (value) =>
      new Date(value) >
      new Date(document.getElementById("disponibilitaDa").value),
    error: "La data deve essere successiva alla data di inizio disponibilità.",
  },
  numeroGiorni: {
    regex: /^[1-9]\d*$/,
    customValidation: (value) => {
      const giorni = parseInt(value, 10);
      const disponibilitaDa = new Date(
        document.getElementById("disponibilitaDa").value
      );
      const disponibilitaA = new Date(
        document.getElementById("disponibilitaA").value
      );
      const maxGiorni =
        Math.ceil((disponibilitaA - disponibilitaDa) / (1000 * 60 * 60 * 24)) +
        1;
      const periodoGiornata = document.getElementById("periodoGiornata");

      if (giorni === 1) {
        periodoGiornata.disabled = false;
        periodoGiornata.required = true;
      } else {
        periodoGiornata.disabled = true;
        periodoGiornata.required = false;
        periodoGiornata.value = ""; // Reset value when disabled
      }

      return giorni <= maxGiorni;
    },
    error:
      "Il numero di giorni non può superare il periodo di disponibilità selezionato.",
  },
  numeroPartecipanti: {
    regex: /^(\d{2,}|[1-9]\d{1,})$/,
    error: "Inserisci un numero di partecipanti minimo di 10.",
  },
  visitatore: {
    customValidation: (value) => value !== "",
    error: "Seleziona un visitatore.",
  },
  periodoGiornata: {
    customValidation: () => {
      const numeroGiorni = parseInt(
        document.getElementById("numeroGiorni").value,
        10
      );
      return (
        numeroGiorni !== 1 ||
        (numeroGiorni === 1 &&
          document.getElementById("periodoGiornata").value !== "")
      );
    },
    error: "Seleziona un periodo della giornata per un singolo giorno.",
  },
  selectLaboratori: {
    customValidation: () => {
      const checkboxes = document.querySelectorAll(
        '#selectLaboratori input[type="checkbox"]'
      );
      return Array.from(checkboxes).some((checkbox) => checkbox.checked);
    },
    error: "Seleziona almeno un laboratorio.",
  },
  privacyPolicy: {
    customValidation: () => {
      const privacyCheckbox = document.getElementById("privacyPolicy");
      return privacyCheckbox.checked;
    },
    error: "Devi accettare i termini della Privacy Policy.",
  },
};

formValidation(
  "bookingForm",
  bookingFields,
  "/api/pub/create-booking",
  "Prenotazione inviata con successo!"
);

// Aggiungi un event listener per gestire la disabilitazione/abilitazione dinamica
document.addEventListener("DOMContentLoaded", () => {
  const numeroGiorni = document.getElementById("numeroGiorni");
  const periodoGiornata = document.getElementById("periodoGiornata");

  numeroGiorni.addEventListener("input", () => {
    const giorni = parseInt(numeroGiorni.value, 10);

    if (giorni === 1) {
      periodoGiornata.disabled = false;
      periodoGiornata.required = true;
    } else {
      periodoGiornata.disabled = true;
      periodoGiornata.required = false;
      periodoGiornata.value = "";
    }
  });
});

/* ------------------------------ INFO FORM ------------------------------ */
const infoFields = {
  nomeInfo: {
    regex: /^[a-zA-ZÀ-ÖØ-öø-ÿ\'\-]{2,}$/,
    error:
      "Il nome deve contenere almeno due lettere. Sono ammessi lettere, accenti e apostrofi.",
  },
  cognomeInfo: {
    regex: /^[a-zA-ZÀ-ÖØ-öø-ÿ\'\-]{2,}$/,
    error:
      "Il cognome deve contenere almeno due lettere. Sono ammessi lettere, accenti e apostrofi.",
  },
  emailInfo: {
    regex: /^[^@\s]+@[^@\s]+\.[^@\s]+$/,
    error: "Inserisci un'email valida.",
  },
  cellulareInfo: {
    regex: /^(\+39\s?)?\d{3}[-\s]?\d{3}[-\s]?\d{4}$/,
    error:
      "Il numero di telefono deve contenere 10 cifre separate da spazi o trattini. Il prefisso +39 è opzionale.",
  },
  messaggio: {
    customValidation: (value) => value.trim().length >= 20,
    error: "Il messaggio deve contenere almeno 20 caratteri.",
  },
  privacyPolicyInfo: {
    customValidation: () => {
      const privacyCheckbox = document.getElementById("privacyPolicyInfo");
      return privacyCheckbox.checked;
    },
    error: "Devi accettare i termini della Privacy Policy.",
  },
};

formValidation(
  "infoForm",
  infoFields,
  "/api/pub/create-info",
  "Informazioni inviate con successo!"
);

/* ------------------------------ ANCHORS LINKS ANIMATIONS ------------------------------ */

// Animates anchor link
document.querySelectorAll('a[href^="#"]').forEach((anchor) => {
  anchor.addEventListener("click", function (e) {
    e.preventDefault();
    const targetId = this.getAttribute("href");
    const targetElement = document.querySelector(targetId);
    if (targetElement) {
      targetElement.scrollIntoView({
        behavior: "smooth",
        block: "start",
      });
    }
  });
});

/* ------------------------------ CAROUSEL ------------------------------ */

// carousel logic
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
