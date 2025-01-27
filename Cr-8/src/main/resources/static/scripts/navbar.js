/**
 * @file: navbar.js
 * @author: CR-8
 * This code includes the logic to toggle the navbar menu from mobile to desktop
 */

/**
 * Toggles the visibility of the navigation menu.
 * Adds or removes the "show" class from the element with the "navbar" class.
 * @returns {void}
 */
function menu() {
  document.querySelector(".navbar").classList.toggle("show");
}
