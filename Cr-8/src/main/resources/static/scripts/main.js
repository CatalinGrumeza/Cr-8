/**
 * @file: main.js
 * @author: CR-8
 * This code includes the logic to validate the book form and the info form input fields
 */

// navbar menu toggle
function menu() {
  document.querySelector(".navbar").classList.toggle("show");
}

const form = document.getElementById("bookingForm");

// form fields
const fields = {
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
    customValidation: (value) => {
      const today = new Date();
      const inputDate = new Date(value);
      return inputDate >= today;
    },
    error: "La data deve essere successiva a oggi.",
  },
  disponibilitaA: {
    customValidation: (value) => {
      const disponibilitaDa = document.getElementById("disponibilitaDa").value;
      const fromDate = new Date(disponibilitaDa);
      const toDate = new Date(value);
      return toDate > fromDate;
    },
    error: "La data deve essere successiva alla data di inizio disponibilità.",
  },
  numeroGiorni: {
    regex: /^[1-9]\d*$/,
    error: "La visita deve essere di almeno 1 giorno.",
  },
  numeroPartecipanti: {
    regex: /^(\d{2,}|[1-9]\d{1,})$/,
    error: "Inserisci un numero di partecipanti minimo di 10.",
  },
  visitatore: {
    customValidation: (value) => value !== "", // Assicura che non sia la stringa vuota
    error: "Seleziona un visitatore.",
  },
  periodoGiornata: {
    customValidation: () => {
      const numeroGiorni = parseInt(
        document.getElementById("numeroGiorni").value,
        10
      );
      const periodoGiornataValue =
        document.getElementById("periodoGiornata").value;
      // Validazione solo se numeroGiorni è 1
      return numeroGiorni === 1 ? periodoGiornataValue !== "" : true;
    },
    error:
      "Seleziona un periodo della giornata se il numero di giorni è uguale a 1.",
  },
};

// fields validation
Object.keys(fields).forEach((fieldId) => {
  const field = document.getElementById(fieldId);
  const errorSpan = document.getElementById(`${fieldId}Error`);

  field.addEventListener("input", () =>
    validateField(field, fields[fieldId], errorSpan)
  );
  field.addEventListener("blur", () =>
    validateField(field, fields[fieldId], errorSpan)
  );
});

function validateField(field, fieldConfig, errorSpan) {
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
}

// if numeroGiorni > 1, periodoGiornata select is disabled
const numeroGiorniField = document.getElementById("numeroGiorni");
const periodoGiornataField = document.getElementById("periodoGiornata");

numeroGiorniField.addEventListener("input", () => {
  const numeroGiorni = parseInt(numeroGiorniField.value, 10);
  if (numeroGiorni > 1) {
    periodoGiornataField.disabled = true;
    periodoGiornataField.value = "";
  } else {
    periodoGiornataField.disabled = false;
  }
});

// validation after form submit
form.addEventListener("submit", async (event) => {
  event.preventDefault();
  let formIsValid = true;

  Object.keys(fields).forEach((fieldId) => {
    const field = document.getElementById(fieldId);
    const errorSpan = document.getElementById(`${fieldId}Error`);
    if (!validateField(field, fields[fieldId], errorSpan)) {
      formIsValid = false;
    }
  });

  const formErrorSpan = document.getElementById("formError");

  if (!formIsValid) {
    alert("attenzione");
    /* formErrorSpan.textContent =
      "Per favore, correggi gli errori evidenziati prima di inviare il modulo."; */
    // return;
  }

  formErrorSpan.textContent = "";

  const formattedDate = new Date().toISOString().split("T")[0];

  const data = {
    name: document.getElementById("nome").value,
    surname: document.getElementById("cognome").value,
    phone: document.getElementById("cellulare").value,
    email: document.getElementById("email").value,
    numberOfDays: document.getElementById("numeroGiorni").value,
    additionalDetails: document.getElementById("richiesteParticolari").value,
    dataFrom: document.getElementById("disponibilitaDa").value,
    dataTo: document.getElementById("disponibilitaA").value,
    participantNumber: document.getElementById("numeroPartecipanti").value,
    bookType: document.getElementById("periodoGiornata").value,
    visitorType: document.getElementById("visitatore").value,
    labs: ["pela patate"],
    createdAt: formattedDate,
  };

  try {
    console.log(data);

    // Effettua la richiesta POST
    const response = await fetch(
      "http://localhost:8080/api/pub/create-booking",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      }
    );

    if (response.ok) {
      // success alert
      alert("Prenotazione inviata con successo!");
      // form fields reset
      form.reset();
    } else {
      // handle response errors
      const errorData = await response.json();
      formErrorSpan.textContent =
        "Errore durante l'invio della prenotazione: " +
        (errorData.message || "riprovare più tardi.");
    }
  } catch (error) {
    formErrorSpan.textContent =
      "Si è verificato un errore. Per favore, riprova più tardi.";
    console.error("Errore:", error);
  }
});

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
