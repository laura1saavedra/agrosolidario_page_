console.log("app.js cargó correctamente ✅");


document.addEventListener("DOMContentLoaded", () => {

    const form = document.querySelector("form");
    const nombreInput = document.getElementById("nombre");
    const correoInput = document.getElementById("correo");
    const passInput = document.getElementById("contrasena");
    const terminosCheck = document.getElementById("terminos");
    const continuarBtn = document.getElementById("continuar-btn");

    if (!form || !continuarBtn) return; // evita errores en otras páginas

    /* =========================
       MENSAJES DE ERROR
       ========================= */
    const crearMensaje = (id, afterEl) => {
        let el = document.getElementById(id);
        if (!el) {
            el = document.createElement("div");
            el.id = id;
            el.style.fontSize = "0.9rem";
            el.style.marginTop = "6px";
            el.style.color = "#b00020";
            afterEl.insertAdjacentElement("afterend", el);
        }
        return el;
    };

    const nombreMsg = crearMensaje("nombre-msg", nombreInput);
    const correoMsg = crearMensaje("correo-msg", correoInput);
    const passMsg = crearMensaje("pass-msg", passInput);
    const terminosMsg = crearMensaje("terminos-msg", terminosCheck.parentElement);

    /* =========================
       VALIDACIONES
       ========================= */
    const validarNombre = () => {
        const ok = nombreInput.value.trim().length > 0;
        nombreMsg.textContent = ok ? "" : "Debes ingresar tu nombre completo.";
        return ok;
    };

    const validarCorreo = () => {
        const valor = correoInput.value.trim();
        const ok = valor.length > 0;
        correoMsg.textContent = ok ? "" : "Debes ingresar un correo electrónico.";
        return ok;
    };

    const validarPassword = () => {
        const pass = passInput.value.trim();
        const ok = pass.length >= 8;
        passMsg.textContent = ok ? "" : "La contraseña debe tener mínimo 8 caracteres.";
        return ok;
    };

    const validarTerminos = () => {
        const ok = terminosCheck.checked;
        terminosMsg.textContent = ok ? "" : "Debes aceptar los términos y condiciones.";
        return ok;
    };

    /* =========================
       EVENTO CONTINUAR
       ========================= */
    continuarBtn.addEventListener("click", (e) => {
        e.preventDefault(); // controlamos el envío manualmente

        const nombreOk = validarNombre();
        const correoOk = validarCorreo();
        const passOk = validarPassword();
        const terminosOk = validarTerminos();

        if (nombreOk && correoOk && passOk && terminosOk) {
            form.submit();
        }
    });

});



document.addEventListener("DOMContentLoaded", () => {
    const ubicacionInput = document.getElementById("ubicacion");
    const habilidadesInput = document.getElementById("habilidades");
    const edadInput = document.getElementById("edad");


    const btnLimpiar = document.getElementById("btnLimpiar");
    const btnBuscar = document.getElementById("btnBuscar");

    // Cards (puede haber 1 o muchas)
    const cards = Array.from(document.querySelectorAll(".busqueda-card"));

    let mensajeBox = document.getElementById("busquedaMensaje");
    if (!mensajeBox) {
        mensajeBox = document.createElement("div");
        mensajeBox.id = "busquedaMensaje";
        mensajeBox.style.display = "none";
        mensajeBox.style.marginTop = "12px";
        mensajeBox.style.padding = "12px";
        mensajeBox.style.borderRadius = "8px";
        mensajeBox.style.background = "#f7f7f7";
        mensajeBox.style.border = "1px solid #ddd";
        mensajeBox.style.fontSize = "0.95rem";

        // Insertar debajo de la zona de acciones
        const actions = document.querySelector(".busqueda__actions");
        actions.insertAdjacentElement("afterend", mensajeBox);
    }

    function normalizar(str) {
        return (str || "").toString().trim().toLowerCase();
    }

    function limpiarCampos() {
        ubicacionInput.value = "";
        habilidadesInput.value = "";
        edadInput.value = "";
    }

    function ocultarMensaje() {
        mensajeBox.style.display = "none";
        mensajeBox.innerHTML = "";
    }

    function mostrarMensajeSinResultados() {
        mensajeBox.innerHTML = `
      <p style="margin:0 0 8px 0;">
        No se encontraron trabajadores con esos filtros.
      </p>
      <button id="btnVolverInicio" class="busqueda__btn">
        Volver al inicio
      </button>
    `;
        mensajeBox.style.display = "block";

        const btnVolverInicio = document.getElementById("btnVolverInicio");
        btnVolverInicio.addEventListener("click", (e) => {
            e.preventDefault();


            cards.forEach(card => {
                card.style.display = "";
            });

            limpiarCampos();
            ocultarMensaje();


            window.scrollTo({ top: 0, behavior: "smooth" });
        });
    }

    function buscarTrabajadores({ ubicacion, habilidades, edad }) {
        let encontrados = 0;

        cards.forEach(card => {
            const textoCard = normalizar(card.innerText);

            const coincideUbicacion = !ubicacion || textoCard.includes(ubicacion);
            const coincideHabilidades = !habilidades || textoCard.includes(habilidades);

            const coincideEdad =
                !edad ||
                textoCard.includes(`${edad} años`) ||
                textoCard.includes(`${edad} año`);

            const mostrar = coincideUbicacion && coincideHabilidades && coincideEdad;


            card.style.display = mostrar ? "" : "none";

            if (mostrar) encontrados++;
        });

        return encontrados;
    }

    btnBuscar.addEventListener("click", (e) => {
        e.preventDefault();
        ocultarMensaje();

        const filtros = {
            ubicacion: normalizar(ubicacionInput.value),
            habilidades: normalizar(habilidadesInput.value),
            edad: edadInput.value ? Number(edadInput.value) : null
        };

        const totalEncontrados = buscarTrabajadores(filtros);

        if (totalEncontrados === 0) {
            mostrarMensajeSinResultados();
        }

        limpiarCampos();
    });

    btnLimpiar.addEventListener("click", (e) => {
        e.preventDefault();

        limpiarCampos();

        cards.forEach(card => {
            card.style.display = "";
        });

        ocultarMensaje();
    });
});

document.addEventListener("DOMContentLoaded", () => {

  const flagPublicada = sessionStorage.getItem("mostrarAvisoPublicada") === "1";
  const enOfertas = window.location.pathname.includes("ofertas.html");

  if (flagPublicada && enOfertas) {
    const alertOverlay = document.getElementById("alertOverlay");
    const alertMsg = document.getElementById("alertMsg");
    const alertBtn = document.getElementById("alertBtn");

    if (alertOverlay && alertMsg && alertBtn) {
      alertMsg.textContent =
        "Su oferta ya está disponible para las personas ideales para ese cargo.";
      alertOverlay.classList.add("show");

      alertBtn.onclick = () => alertOverlay.classList.remove("show");
    }

    sessionStorage.removeItem("mostrarAvisoPublicada");
  }

  const form = document.getElementById("form-publicar");
  if (!form) return; 

  const alertOverlay = document.getElementById("alertOverlay");
  const alertBox = alertOverlay.querySelector(".alert-box");
  const alertMsg = document.getElementById("alertMsg");
  const alertBtn = document.getElementById("alertBtn");

  const originalOkBtn = alertBtn;

  function openOverlay() { alertOverlay.classList.add("show"); }
  function closeOverlay() { alertOverlay.classList.remove("show"); }

  function showAlert(msg){
    alertMsg.textContent = msg;

    const old = alertBox.querySelector(".confirm-actions");
    if (old) old.remove();

    originalOkBtn.style.display = "inline-flex";
    originalOkBtn.textContent = "OK";

    openOverlay();
  }

  originalOkBtn.addEventListener("click", closeOverlay);

  function showConfirm(msg, onYes){
    alertMsg.textContent = msg;
    originalOkBtn.style.display = "none";

    const old = alertBox.querySelector(".confirm-actions");
    if (old) old.remove();

    const actions = document.createElement("div");
    actions.className = "confirm-actions";
    actions.style.display = "flex";
    actions.style.justifyContent = "center";
    actions.style.gap = "12px";
    actions.style.marginTop = "8px";

    const yesBtn = document.createElement("button");
    yesBtn.type = "button";
    yesBtn.className = "alert-btn";
    yesBtn.textContent = "Sí";

    const noBtn = document.createElement("button");
    noBtn.type = "button";
    noBtn.className = "alert-btn";
    noBtn.textContent = "No";

    actions.appendChild(yesBtn);
    actions.appendChild(noBtn);
    alertBox.appendChild(actions);

    yesBtn.addEventListener("click", () => {
      closeOverlay();
      onYes();
    }, { once: true });

    noBtn.addEventListener("click", closeOverlay, { once: true });

    openOverlay();
  }

  form.addEventListener("submit", (event) => {
    event.preventDefault();

    const titulo = document.getElementById("titulo").value.trim();
    const descripcion = document.getElementById("descripcion").value.trim();
    const requisitos = document.getElementById("requisitos").value.trim();
    const salario = document.getElementById("salario").value.trim();
    const num = document.getElementById("num").value.trim();
    const fecha = document.getElementById("fecha").value.trim();

    if (!titulo || !descripcion || !requisitos || !salario || !num || !fecha) {
      showAlert("Por favor, completa todos los campos antes de publicar la oferta.");
      return;
    }

    const regexFecha = /^\d{2}\/\d{2}\/\d{4}$/;
    if (!regexFecha.test(fecha)) {
      showAlert("La fecha debe tener el formato dd/mm/aaaa.");
      return;
    }

    showConfirm("¿Estás seguro de realizar la publicación de la oferta?", () => {

      sessionStorage.setItem("mostrarAvisoPublicada", "1");

      window.location.href = "./contenido/ofertas.jsp";
    });

  });

});

document.addEventListener("DOMContentLoaded", function () {
    const exitBtn = document.querySelector(".exit-btn");
    if (exitBtn) {
        exitBtn.addEventListener("click", function () {
            window.location.href = "../index.jsp";
        });
    }
});

document.addEventListener("DOMContentLoaded", () => {
  const finalizarLinks = document.querySelectorAll(".mis-ofertas__finalizar");

  if (!finalizarLinks.length) return;

  const overlay = document.createElement("div");
  overlay.className = "alert-overlay";
  overlay.innerHTML = `
    <div class="alert-box" role="dialog" aria-modal="true" aria-labelledby="alertTitle">
      <h3 id="alertTitle" class="alert-title">¿Finalizar oferta?</h3>
      <p class="alert-message">¿Estás seguro de que deseas finalizar esta oferta?</p>
      <div style="display:flex; gap:12px; justify-content:center; flex-wrap:wrap;">
        <button type="button" class="alert-btn alert-btn--no">No</button>
        <button type="button" class="alert-btn alert-btn--yes">Sí</button>
      </div>
    </div>
  `;
  document.body.appendChild(overlay);

  const btnNo = overlay.querySelector(".alert-btn--no");
  const btnYes = overlay.querySelector(".alert-btn--yes");

  let destinoHref = "./contenido/fin-oferta.jsp"; 
  let lastFocused = null;

  const abrirAlerta = (href) => {
    destinoHref = href || destinoHref;
    lastFocused = document.activeElement;

    overlay.classList.add("show");
    btnNo.focus();
    document.addEventListener("keydown", onKeydown);
  };

  const cerrarAlerta = () => {
    overlay.classList.remove("show");
    document.removeEventListener("keydown", onKeydown);
    if (lastFocused) lastFocused.focus();
  };

  const onKeydown = (e) => {
    if (e.key === "Escape") {
      e.preventDefault();
      cerrarAlerta();
    }
  };

  finalizarLinks.forEach((link) => {
    link.addEventListener("click", (e) => {
      e.preventDefault();
      abrirAlerta(link.getAttribute("href"));
    });
  });

  btnNo.addEventListener("click", cerrarAlerta);

  btnYes.addEventListener("click", () => {
    window.location.href = destinoHref;
  });

  overlay.addEventListener("click", (e) => {
    if (e.target === overlay) cerrarAlerta();
  });
});

document.addEventListener("DOMContentLoaded", () => {
  if (!window.location.pathname.includes("fin-oferta.jsp")) return;

  const btnVolver = document.querySelector(".seleccion-persona__btn");
  if (!btnVolver) return;

  btnVolver.addEventListener("click", (e) => {
    e.preventDefault();

    const hoy = new Date();
    const dia = String(hoy.getDate()).padStart(2, '0');
    const mes = String(hoy.getMonth() + 1).padStart(2, '0'); 
    const año = String(hoy.getFullYear()).slice(2);

    const fechaTexto = `${dia}/${mes}/${año}`; 

    localStorage.setItem("fechaFinOferta", fechaTexto);

    window.location.href = "./contenido/lista-oferta.jsp";
  });
});

document.addEventListener("DOMContentLoaded", () => {
  if (!window.location.pathname.includes("lista-oferta.jsp")) return;

  const fechaGuardada = localStorage.getItem("fechaFinOferta");
  if (!fechaGuardada) return;

  const botonesFinalizar = document.querySelectorAll(".mis-ofertas__finalizar");

  botonesFinalizar.forEach((btn) => {
    const fila = btn.closest("tr");
    if (!fila) return;

    const celdaEstado = fila.querySelector(".mis-ofertas__estado");
    if (celdaEstado) celdaEstado.textContent = "Finalizada";

    const celdaFinalizar = btn.parentElement;
    if (celdaFinalizar) {
      celdaFinalizar.textContent = fechaGuardada;
      celdaFinalizar.classList.add("mis-ofertas__fecha");
    }
  });

  localStorage.removeItem("fechaFinOferta");
});

document.addEventListener("DOMContentLoaded", () => {
  if (!window.location.pathname.includes("lista-oferta.jsp")) return;

  const fechaGuardada = localStorage.getItem("fechaFinOferta");

  // Si viene una fecha desde fin-oferta.html, finalizamos ofertas activas
  if (fechaGuardada) {
    const botonesFinalizar = document.querySelectorAll(".mis-ofertas__finalizar");

    botonesFinalizar.forEach((btn) => {
      const fila = btn.closest("tr");
      if (!fila) return;

      
      const celdaEstado = fila.querySelector(".mis-ofertas__estado");
      if (celdaEstado) celdaEstado.textContent = "Finalizada";

    
      const celdaFinalizar = btn.parentElement;
      if (celdaFinalizar) {
        celdaFinalizar.textContent = fechaGuardada;
        celdaFinalizar.classList.add("mis-ofertas__fecha");
      }
    });

    
    localStorage.removeItem("fechaFinOferta");
  }

  const botonesFinalizarRestantes = document.querySelectorAll(".mis-ofertas__finalizar");

  if (botonesFinalizarRestantes.length === 0) {

    const botonesRevisar = document.querySelectorAll(".btn-dark--review");

    botonesRevisar.forEach(btn => {
      btn.classList.add("disabled-review");
      btn.style.pointerEvents = "none";  
      btn.style.opacity = "0.5";          
    });
  }
});

document.addEventListener("DOMContentLoaded", () => {

  const btnContinuar = document.querySelector(".btn-continuar");

  const overlay = document.createElement("div");
  overlay.className = "alert-overlay";
  overlay.innerHTML = `
    <div class="alert-box" role="alertdialog" aria-modal="true">
      <h3 class="alert-title">Atención</h3>
      <p class="alert-message"></p>
      <button class="alert-btn" type="button">Entendido</button>
    </div>
  `;
  document.body.appendChild(overlay);

  const alertMsg = overlay.querySelector(".alert-message");
  const alertBtn = overlay.querySelector(".alert-btn");

  function showAlert(message) {
    alertMsg.textContent = message;
    overlay.classList.add("show");
    alertBtn.focus();
  }

  function hideAlert() {
    overlay.classList.remove("show");
  }

  alertBtn.addEventListener("click", hideAlert);
  overlay.addEventListener("click", (e) => {
    if (e.target === overlay) hideAlert(); 
  });
  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape" && overlay.classList.contains("show")) hideAlert();
  });

  btnContinuar.addEventListener("click", (e) => {

    const campos = document.querySelectorAll("input, textarea");
    let formularioCompleto = true;

    campos.forEach(campo => {
      if (campo.type !== "file" && campo.value.trim() === "") {
        formularioCompleto = false;
      }
      if (campo.type === "file" && campo.files.length === 0) {
        formularioCompleto = false;
      }
    });

    if (!formularioCompleto) {
      e.preventDefault();
      showAlert("Todos los campos deben estar completos para continuar");
    }
  });

});

document.addEventListener("DOMContentLoaded", () => {

  const btnContinuar = document.querySelector(".btn"); 

  const overlay = document.createElement("div");
  overlay.className = "alert-overlay";
  overlay.innerHTML = `
    <div class="alert-box" role="alertdialog" aria-modal="true">
      <h3 class="alert-title">Atención</h3>
      <p class="alert-message"></p>
      <button class="alert-btn" type="button">Entendido</button>
    </div>
  `;
  document.body.appendChild(overlay);

  const alertMsg = overlay.querySelector(".alert-message");
  const alertBtn = overlay.querySelector(".alert-btn");

  function showAlert(message) {
    alertMsg.textContent = message;
    overlay.classList.add("show");
    alertBtn.focus();
  }

  function hideAlert() {
    overlay.classList.remove("show");
  }

  alertBtn.addEventListener("click", hideAlert);
  overlay.addEventListener("click", (e) => {
    if (e.target === overlay) hideAlert();
  });
  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape" && overlay.classList.contains("show")) hideAlert();
  });

  btnContinuar.addEventListener("click", (e) => {

    const campos = document.querySelectorAll("input, textarea");
    let formularioCompleto = true;

    campos.forEach(campo => {
      if (campo.type !== "file" && campo.value.trim() === "") {
        formularioCompleto = false;
      }
      if (campo.type === "file" && campo.files.length === 0) {
        formularioCompleto = false;
      }
    });

    if (!formularioCompleto) {
      e.preventDefault(); 
      showAlert("Todos los campos deben estar completos para continuar");
    }
  });

});

document.addEventListener("DOMContentLoaded", () => {

    const soloNumeros = document.querySelectorAll(
        'input[type="text"][placeholder="Colombia, Bogotá D.C"] ~ input[type="text"], ' + 
        'input[type="text"]:nth-of-type(1), ' +
        'input[type="number"]'
    );

    function permitirSoloNumeros(event) {
        event.target.value = event.target.value.replace(/\D/g, "");
    }

    soloNumeros.forEach(input => {
        input.addEventListener("input", permitirSoloNumeros);
    });

});

document.addEventListener("DOMContentLoaded", () => {

    // 🔒 Protección: solo ejecuta en la página de login
    const form = document.querySelector(".seccion-formulario-inicio-sesion form");
    const emailInput = document.getElementById("correo-inicio");
    const passInput = document.getElementById("contrasena-inicio");
    const loginBtn = document.getElementById("iniciar");

    if (!form || !loginBtn) return; // evita errores en otras páginas

    /* =========================
       MENSAJES DE ERROR
       ========================= */
    const crearMensaje = (id, afterEl) => {
        let el = document.getElementById(id);
        if (!el) {
            el = document.createElement("div");
            el.id = id;
            el.style.fontSize = "0.9rem";
            el.style.marginTop = "6px";
            el.style.color = "#b00020";
            afterEl.insertAdjacentElement("afterend", el);
        }
        return el;
    };

    const emailMsg = crearMensaje("login-email-msg", emailInput);
    const passMsg = crearMensaje("login-pass-msg", passInput);

    /* =========================
       VALIDACIONES
       ========================= */
    const validarEmail = () => {
        const ok = emailInput.value.trim().length > 0;
        emailMsg.textContent = ok ? "" : "Debes ingresar tu correo.";
        return ok;
    };

    const validarPassword = () => {
        const ok = passInput.value.trim().length > 0;
        passMsg.textContent = ok ? "" : "Debes ingresar tu contraseña.";
        return ok;
    };

    emailInput.addEventListener("input", validarEmail);
    passInput.addEventListener("input", validarPassword);

    /* =========================
       EVENTO LOGIN
       ========================= */
    loginBtn.addEventListener("click", (e) => {
        e.preventDefault(); // evitamos submit automático

        const emailOk = validarEmail();
        const passOk = validarPassword();

        if (emailOk && passOk) {
            form.submit();
        }
    });

});






