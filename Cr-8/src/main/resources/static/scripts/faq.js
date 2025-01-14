/**
 * @file: faq.js
 * @author: CR-8
 * This code includes the logic to validate the info form input fields in the faq page
 */

const form = document.getElementById("infoForm");

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
  email: {
    regex: /^[^@\s]+@[^@\s]+\.[^@\s]+$/,
    error: "Inserisci un'email valida.",
  },
  cellulare: {
    regex: /^(\+39\s?)?\d{3}[-\s]?\d{3}[-\s]?\d{4}$/,
    error:
      "Il numero di telefono deve contenere 10 cifre separate da spazi o trattini. Il prefisso +39 è opzionale.",
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

// validation after form submit
form.addEventListener("submit", (event) => {
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
    event.preventDefault();
    formErrorSpan.textContent =
      "Per favore, correggi gli errori evidenziati prima di inviare il modulo.";
  }
});
