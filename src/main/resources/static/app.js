const state = {
  token: localStorage.getItem("os-token") || "",
  apiBase: localStorage.getItem("os-api-base") || "",
  currentView: "cadastros",
  currentEntity: "cliente",
  editId: null,
  editOrderId: null,
  entityRows: [],
  orderStep: 0,
  refs: { clientes: [], tecnicos: [], equipamentos: [], servicos: [] },
  orders: [],
};

const statuses = [
  "AGUARDANDO_APROVACAO",
  "APROVADA",
  "EM_ANDAMENTO",
  "AGUARDANDO_PECA",
  "FINALIZADA",
  "FATURADA",
  "ENTREGUE",
  "CANCELADA",
];

const orderSteps = [
  { title: "Cliente", hint: "Cliente e equipamento" },
  { title: "Servico", hint: "Servico e relato" },
  { title: "Revisao", hint: "Status e pagamento" },
];

const entityConfig = {
  cliente: {
    title: "Novo cliente",
    subtitle: "Dados comerciais e contato.",
    tableTitle: "Clientes cadastrados",
    endpoint: "/cliente",
    update: (id) => `/cliente/atualizar/${id}`,
    icon: "user",
    fields: [
      ["nome", "Nome", "text", true],
      ["email", "E-mail", "email", true],
      ["cpfcnpj", "CPF/CNPJ", "text", true],
      ["telefone", "Telefone", "tel", true],
      ["ativo", "Ativo", "checkbox", false],
    ],
    columns: ["id", "nome", "cpfcnpj", "telefone", "email", "ativo"],
    map: (form) => ({ ...form, ativo: Boolean(form.ativo) }),
    actions: ["toggle-active"],
  },
  tecnico: {
    title: "Novo tecnico",
    subtitle: "Equipe responsavel pelo atendimento.",
    tableTitle: "Tecnicos cadastrados",
    endpoint: "/tecnico",
    delete: (id) => `/tecnico/deletar/${id}`,
    icon: "wrench",
    fields: [["nome", "Nome", "text", true]],
    columns: ["id", "nome"],
    actions: ["delete"],
  },
  equipamento: {
    title: "Novo equipamento",
    subtitle: "Vincule o equipamento a um cliente.",
    tableTitle: "Equipamentos cadastrados",
    endpoint: "/equipamento",
    update: () => "/equipamento",
    delete: (id) => `/equipamento/deletar/${id}`,
    icon: "cpu",
    fields: [
      ["clienteId", "Cliente", "cliente-select", true],
      ["modelo", "Modelo", "text", true],
      ["marca", "Marca", "text", true],
      ["ano", "Ano", "number", true],
      ["emmanutencao", "Em manutencao", "checkbox", false],
    ],
    columns: ["id", "clienteId.nome", "modelo", "marca", "ano", "emmanutencao"],
    map: (form) => ({
      ...form,
      clienteId: Number(form.clienteId),
      ano: Number(form.ano),
      emmanutencao: Boolean(form.emmanutencao),
    }),
    actions: ["toggle-maintenance", "delete"],
  },
  servico: {
    title: "Novo servico",
    subtitle: "Catalogo de servicos e valores.",
    tableTitle: "Servicos cadastrados",
    endpoint: "/servico",
    update: (id) => `/servico/editar/${id}`,
    delete: (id) => `/servico/deletar/${id}`,
    icon: "clipboard",
    fields: [
      ["descricao", "Descricao", "text", true],
      ["valor", "Valor", "number", true],
    ],
    columns: ["id", "descricao", "valor"],
    map: (form) => ({ ...form, valor: Number(form.valor) }),
    actions: ["edit", "delete"],
  },
};

const icons = {
  archive: '<svg viewBox="0 0 24 24"><path d="M21 8v13H3V8"/><path d="M1 3h22v5H1z"/><path d="M10 12h4"/></svg>',
  "file-plus": '<svg viewBox="0 0 24 24"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><path d="M14 2v6h6"/><path d="M12 18v-6"/><path d="M9 15h6"/></svg>',
  kanban: '<svg viewBox="0 0 24 24"><path d="M6 5v14"/><path d="M12 5v14"/><path d="M18 5v14"/><path d="M3 5h18v14H3z"/></svg>',
  key: '<svg viewBox="0 0 24 24"><circle cx="7.5" cy="15.5" r="5.5"/><path d="M12 11l9-9"/><path d="M17 6l2 2"/><path d="M14 9l2 2"/></svg>',
  "log-out": '<svg viewBox="0 0 24 24"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><path d="M16 17l5-5-5-5"/><path d="M21 12H9"/></svg>',
  refresh: '<svg viewBox="0 0 24 24"><path d="M21 12a9 9 0 0 1-15.5 6.2"/><path d="M3 12A9 9 0 0 1 18.5 5.8"/><path d="M18 2v4h4"/><path d="M6 22v-4H2"/></svg>',
  save: '<svg viewBox="0 0 24 24"><path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"/><path d="M17 21v-8H7v8"/><path d="M7 3v5h8"/></svg>',
  x: '<svg viewBox="0 0 24 24"><path d="M18 6 6 18"/><path d="m6 6 12 12"/></svg>',
  user: '<svg viewBox="0 0 24 24"><path d="M20 21a8 8 0 0 0-16 0"/><circle cx="12" cy="7" r="4"/></svg>',
  wrench: '<svg viewBox="0 0 24 24"><path d="M14.7 6.3a5 5 0 0 0-6.4 6.4L3 18l3 3 5.3-5.3a5 5 0 0 0 6.4-6.4l-3 3-3-3z"/></svg>',
  cpu: '<svg viewBox="0 0 24 24"><rect x="6" y="6" width="12" height="12" rx="2"/><path d="M9 1v3"/><path d="M15 1v3"/><path d="M9 20v3"/><path d="M15 20v3"/><path d="M20 9h3"/><path d="M20 14h3"/><path d="M1 9h3"/><path d="M1 14h3"/></svg>',
  clipboard: '<svg viewBox="0 0 24 24"><path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2"/><rect x="8" y="2" width="8" height="4" rx="1"/></svg>',
  plus: '<svg viewBox="0 0 24 24"><path d="M12 5v14"/><path d="M5 12h14"/></svg>',
  eye: '<svg viewBox="0 0 24 24"><path d="M2 12s3.5-7 10-7 10 7 10 7-3.5 7-10 7-10-7-10-7z"/><circle cx="12" cy="12" r="3"/></svg>',
  check: '<svg viewBox="0 0 24 24"><path d="M20 6 9 17l-5-5"/></svg>',
  ban: '<svg viewBox="0 0 24 24"><circle cx="12" cy="12" r="9"/><path d="m5.7 5.7 12.6 12.6"/></svg>',
};

const $ = (selector) => document.querySelector(selector);
const $$ = (selector) => Array.from(document.querySelectorAll(selector));

function init() {
  $("#apiBase").value = state.apiBase;
  renderIcons();
  renderEntityChoices();
  renderEntityForm();
  renderOrderSteps();
  renderStatusSelect();
  updateAuthState();
  bindEvents();
  loadEntityList();
  loadOrders();
}

function bindEvents() {
  $$(".nav-item").forEach((button) => button.addEventListener("click", () => switchView(button.dataset.view)));
  $("#apiBase").addEventListener("change", (event) => {
    state.apiBase = event.target.value.trim();
    localStorage.setItem("os-api-base", state.apiBase);
  });
  $("#openLogin").addEventListener("click", () => $("#loginDialog").showModal());
  $("#logoutBtn").addEventListener("click", logout);
  $("#reloadCurrent").addEventListener("click", loadEntityList);
  $("#loadRefs").addEventListener("click", loadReferences);
  $("#refreshTriage").addEventListener("click", loadOrders);
  $("#entityForm").addEventListener("submit", submitEntity);
  $("#orderForm").addEventListener("submit", submitOrder);
  $("#loginForm").addEventListener("submit", login);
  $("#addItem").addEventListener("click", addOrderItem);
  $("#prevStep").addEventListener("click", () => changeOrderStep(-1));
  $("#nextStep").addEventListener("click", () => changeOrderStep(1));
  $("#orderForm").addEventListener("input", renderOrderSummary);
  $("#orderForm").addEventListener("change", renderOrderSummary);
  $("#closeDetails").addEventListener("click", () => $("#orderDialog").close());
}

function renderIcons(root = document) {
  root.querySelectorAll("[data-icon]").forEach((el) => {
    el.innerHTML = icons[el.dataset.icon] || "";
  });
}

function switchView(view) {
  state.currentView = view;
  $$(".view").forEach((el) => el.classList.toggle("active", el.id === view));
  $$(".nav-item").forEach((el) => el.classList.toggle("active", el.dataset.view === view));
  const titles = {
    cadastros: ["Cadastros", "Central de cadastro"],
    ordem: ["Ordem de servico", "Abertura de OS"],
    triagem: ["Triagem", "Status das ordens"],
  };
  $("#sectionEyebrow").textContent = titles[view][0];
  $("#sectionTitle").textContent = titles[view][1];
  if (view === "ordem") loadReferences();
  if (view === "triagem") loadOrders();
}

function renderEntityChoices() {
  const target = $("#entityChoices");
  target.innerHTML = Object.entries(entityConfig).map(([key, config]) => `
    <button class="choice-card ${key === state.currentEntity ? "active" : ""}" data-entity="${key}">
      <span data-icon="${config.icon}"></span>
      <span>
        <strong>${config.title.replace("Novo ", "")}</strong>
        <small>${config.subtitle}</small>
      </span>
    </button>
  `).join("");
  renderIcons(target);
  target.querySelectorAll("button").forEach((button) => {
    button.addEventListener("click", async () => {
      state.currentEntity = button.dataset.entity;
      state.editId = null;
      renderEntityChoices();
      renderEntityForm();
      if (state.currentEntity === "equipamento") await loadClientesRef();
      loadEntityList();
    });
  });
}

function renderEntityForm() {
  const config = entityConfig[state.currentEntity];
  $("#formTitle").textContent = state.editId ? `Editar ${config.title.replace("Novo ", "")}` : config.title;
  $("#formSubtitle").textContent = config.subtitle;
  $("#tableTitle").textContent = config.tableTitle;
  $("#entityForm").innerHTML = config.fields.map(([name, label, type, required]) => fieldHtml(name, label, type, required)).join("") + `
    <div class="wide form-actions">
      ${state.editId ? '<button class="ghost-light-btn" id="cancelEdit" type="button">Cancelar</button>' : ""}
      <button class="primary-btn" type="submit">
        <span data-icon="save"></span>
        ${state.editId ? "Atualizar cadastro" : "Salvar cadastro"}
      </button>
    </div>
  `;
  renderIcons($("#entityForm"));
  const cancel = $("#cancelEdit");
  if (cancel) cancel.addEventListener("click", cancelEdit);
}

function fieldHtml(name, label, type, required) {
  if (type === "checkbox") {
    return `<label>${label}<select name="${name}"><option value="true">Sim</option><option value="false">Nao</option></select></label>`;
  }
  if (type === "cliente-select") {
    return `<label>${label}<select name="${name}" ${required ? "required" : ""}><option value="">Carregue os clientes</option></select></label>`;
  }
  const step = type === "number" && name === "valor" ? ' step="0.01" min="0"' : type === "number" ? ' min="0"' : "";
  return `<label>${label}<input name="${name}" type="${type}" ${required ? "required" : ""}${step}></label>`;
}

function renderStatusSelect() {
  const select = $('#orderForm select[name="status"]');
  select.innerHTML = statuses.map((status) => `<option value="${status}">${formatStatus(status)}</option>`).join("");
  select.value = "AGUARDANDO_APROVACAO";
}

function renderOrderSteps() {
  $("#orderSteps").innerHTML = orderSteps.map((step, index) => `
    <button type="button" class="step ${index === state.orderStep ? "active" : ""}" data-step="${index}">
      <strong>${index + 1}. ${step.title}</strong>
      <span>${step.hint}</span>
    </button>
  `).join("");
  $("#orderSteps").querySelectorAll(".step").forEach((button) => {
    button.addEventListener("click", () => setOrderStep(Number(button.dataset.step)));
  });
  setOrderStep(state.orderStep);
}

function setOrderStep(step) {
  state.orderStep = Math.max(0, Math.min(orderSteps.length - 1, step));
  $$(".order-step").forEach((el, index) => el.classList.toggle("active", index === state.orderStep));
  $$("#orderSteps .step").forEach((el, index) => {
    el.classList.toggle("active", index === state.orderStep);
    el.classList.toggle("done", index < state.orderStep);
  });
  $("#prevStep").disabled = state.orderStep === 0;
  $("#nextStep").hidden = state.orderStep === orderSteps.length - 1;
  $("#submitOrder").hidden = state.orderStep !== orderSteps.length - 1;
  $("#submitOrder").innerHTML = `
    <span data-icon="save"></span>
    ${state.editOrderId ? "Atualizar ordem" : "Abrir ordem"}
  `;
  renderIcons($("#submitOrder"));
  renderOrderSummary();
}

function changeOrderStep(delta) {
  if (delta > 0 && !validateStep(state.orderStep)) return;
  setOrderStep(state.orderStep + delta);
}

function validateStep(step) {
  const fields = $$(".order-step")[step].querySelectorAll("input, select, textarea");
  for (const field of fields) {
    if (!field.checkValidity()) {
      field.reportValidity();
      return false;
    }
  }
  return true;
}

async function login(event) {
  event.preventDefault();
  const payload = formToObject(new FormData(event.currentTarget));
  try {
    const data = await request("/auth/login", { method: "POST", body: payload, auth: false });
    state.token = data.token;
    localStorage.setItem("os-token", state.token);
    $("#loginDialog").close();
    updateAuthState();
    toast("Login realizado.");
  } catch (error) {
    toast(error.message);
  }
}

function logout() {
  state.token = "";
  localStorage.removeItem("os-token");
  updateAuthState();
  toast("Sessao encerrada.");
}

function updateAuthState() {
  const el = $("#authState");
  el.textContent = state.token ? "Autenticado" : "Sem login";
  el.classList.toggle("on", Boolean(state.token));
}

async function submitEntity(event) {
  event.preventDefault();
  const config = entityConfig[state.currentEntity];
  const form = formToObject(new FormData(event.currentTarget));
  const payload = config.map ? config.map(form) : form;
  try {
    const editing = Boolean(state.editId);
    const endpoint = editing && config.update ? config.update(state.editId) : config.endpoint;
    await request(endpoint, { method: editing ? "PUT" : "POST", body: payload });
    event.currentTarget.reset();
    state.editId = null;
    renderEntityForm();
    toast(editing ? "Cadastro atualizado." : "Cadastro salvo.");
    await loadEntityList();
    if (state.currentEntity === "cliente") await loadClientesRef();
  } catch (error) {
    toast(error.message);
  }
}

function startEdit(row) {
  state.editId = row.id;
  renderEntityForm();
  const form = $("#entityForm");
  Object.entries(row).forEach(([key, value]) => {
    const field = form.elements[key];
    if (!field) return;
    field.value = typeof value === "boolean" ? String(value) : value ?? "";
  });
}

function cancelEdit() {
  state.editId = null;
  $("#entityForm").reset();
  renderEntityForm();
}

async function loadClientesRef() {
  try {
    state.refs.clientes = await request("/cliente");
    const select = $('#entityForm select[name="clienteId"]');
    if (select) fillSelect(select, state.refs.clientes, (item) => `${item.nome} - ${item.cpfcnpj || item.id}`);
  } catch (error) {
    toast(error.message);
  }
}

async function loadEntityList() {
  const config = entityConfig[state.currentEntity];
  try {
    const data = await request(config.endpoint);
    renderEntityTable(Array.isArray(data) ? data : []);
  } catch (error) {
    renderEntityTable([]);
    toast(error.message);
  }
}

function renderEntityTable(rows) {
  const config = entityConfig[state.currentEntity];
  state.entityRows = rows;
  const hasActions = (config.actions || []).length > 0;
  $("#entityTableHead").innerHTML = `<tr>${config.columns.map((col) => `<th>${headerLabel(col)}</th>`).join("")}${hasActions ? "<th>Acoes</th>" : ""}</tr>`;
  $("#entityTableBody").innerHTML = rows.length
    ? rows.map((row) => `<tr>${config.columns.map((col) => `<td>${formatValue(getPath(row, col))}</td>`).join("")}${hasActions ? `<td>${entityActions(row)}</td>` : ""}</tr>`).join("")
    : `<tr><td class="empty" colspan="${config.columns.length + (hasActions ? 1 : 0)}">Nenhum registro encontrado</td></tr>`;
  bindEntityActions();
}

function entityActions(row) {
  const config = entityConfig[state.currentEntity];
  return `
    <div class="table-actions">
      ${(config.actions || []).map((action) => {
        if (action === "edit") return `<button class="mini-btn" type="button" data-entity-action="edit" data-id="${row.id}">Editar</button>`;
        if (action === "delete") return `<button class="mini-btn danger" type="button" data-entity-action="delete" data-id="${row.id}">Excluir</button>`;
        if (action === "toggle-active") return `<button class="mini-btn" type="button" data-entity-action="toggle-active" data-id="${row.id}">${row.ativo ? "Desativar" : "Ativar"}</button>`;
        if (action === "toggle-maintenance") return `<button class="mini-btn" type="button" data-entity-action="toggle-maintenance" data-id="${row.id}">${row.emmanutencao ? "Liberar" : "Manutencao"}</button>`;
        return "";
      }).join("")}
    </div>
  `;
}

function bindEntityActions() {
  $("#entityTableBody").querySelectorAll("[data-entity-action]").forEach((button) => {
    button.addEventListener("click", () => handleEntityAction(button.dataset.entityAction, Number(button.dataset.id)));
  });
}

async function handleEntityAction(action, id) {
  const row = state.entityRows.find((item) => item.id === id);
  if (!row) return;
  if (action === "edit") return startEdit(row);
  if (action === "delete") return deleteEntity(row);
  if (action === "toggle-active") return toggleCliente(row);
  if (action === "toggle-maintenance") return toggleEquipamento(row);
}

async function deleteEntity(row) {
  const config = entityConfig[state.currentEntity];
  if (!config.delete) return;
  if (!confirm(`Excluir registro #${row.id}?`)) return;
  try {
    await request(config.delete(row.id), { method: "DELETE" });
    toast("Registro excluido.");
    await loadEntityList();
  } catch (error) {
    toast(error.message);
  }
}

async function toggleCliente(row) {
  try {
    await request(entityConfig.cliente.update(row.id), {
      method: "PUT",
      body: {
        nome: row.nome,
        email: row.email,
        cpfcnpj: row.cpfcnpj,
        telefone: row.telefone,
        ativo: !row.ativo,
      },
    });
    toast(row.ativo ? "Cliente desativado." : "Cliente ativado.");
    await loadEntityList();
  } catch (error) {
    toast(error.message);
  }
}

async function toggleEquipamento(row) {
  try {
    await request(entityConfig.equipamento.update(row.id), {
      method: "PUT",
      body: {
        id: row.id,
        clienteId: row.clienteId,
        modelo: row.modelo,
        ano: row.ano,
        marca: row.marca,
        emmanutencao: !row.emmanutencao,
      },
    });
    toast(row.emmanutencao ? "Equipamento liberado." : "Equipamento em manutencao.");
    await loadEntityList();
  } catch (error) {
    toast(error.message);
  }
}

async function loadReferences() {
  try {
    const [clientes, tecnicos, equipamentos, servicos] = await Promise.all([
      request("/cliente"),
      request("/tecnico"),
      request("/equipamento"),
      request("/servico"),
    ]);
    state.refs = { clientes, tecnicos, equipamentos, servicos };
    fillOrderSelects();
    if (!$("#itemsList").children.length) addOrderItem();
    renderOrderSummary();
    toast("Referencias atualizadas.");
  } catch (error) {
    toast(error.message);
  }
}

function fillOrderSelects() {
  fillSelect($('select[name="clienteid"]'), state.refs.clientes, (item) => `${item.nome} - ${item.cpfcnpj || item.id}`);
  fillSelect($('select[name="tecnicoid"]'), state.refs.tecnicos, (item) => item.nome);
  fillSelect($('select[name="equipamentoid"]'), state.refs.equipamentos, (item) => {
    const cliente = getPath(item, "clienteId.nome");
    const equipamento = `${item.marca || ""} ${item.modelo || ""}`.trim() || `Equipamento ${item.id}`;
    return cliente ? `${equipamento} - ${cliente}` : equipamento;
  });
  fillSelect($('select[name="servicoid"]'), state.refs.servicos, (item) => `${item.descricao} - ${money(item.valor)}`);
  $$(".item-service").forEach((select) => fillItemSelect(select));
}

function fillSelect(select, rows, labelFn) {
  select.innerHTML = rows.length
    ? `<option value="">Selecione</option>${rows.map((item) => `<option value="${item.id}">${escapeHtml(labelFn(item))}</option>`).join("")}`
    : '<option value="">Sem registros</option>';
}

function fillItemSelect(select) {
  select.innerHTML = state.refs.servicos.length
    ? state.refs.servicos.map((item) => `<option value="${item.id}">${escapeHtml(item.descricao)} - ${money(item.valor)}</option>`).join("")
    : '<option value="">Sem servicos</option>';
}

function addOrderItem(item = null) {
  const row = document.createElement("div");
  row.className = "item-row";
  row.innerHTML = `
    <label>Servico adicional
      <select class="item-service"></select>
    </label>
    <label>Quantidade
      <input class="item-qty" type="number" min="1" value="1">
    </label>
    <button type="button" class="icon-btn remove-item" title="Remover item">
      <span data-icon="x"></span>
    </button>
  `;
  $("#itemsList").appendChild(row);
  fillItemSelect(row.querySelector(".item-service"));
  if (item) {
    row.querySelector(".item-service").value = getPath(item, "servico.id") || item.servicoid || item.servicoId || "";
    row.querySelector(".item-qty").value = item.quantidade || 1;
  }
  row.querySelector(".remove-item").addEventListener("click", () => {
    row.remove();
    renderOrderSummary();
  });
  row.addEventListener("change", renderOrderSummary);
  row.addEventListener("input", renderOrderSummary);
  renderIcons(row);
  renderOrderSummary();
}

function getSelectedOrder() {
  const form = formToObject(new FormData($("#orderForm")));
  return {
    cliente: findById(state.refs.clientes, form.clienteid),
    tecnico: findById(state.refs.tecnicos, form.tecnicoid),
    equipamento: findById(state.refs.equipamentos, form.equipamentoid),
    servico: findById(state.refs.servicos, form.servicoid),
    status: form.status || "AGUARDANDO_APROVACAO",
    valorPago: Number(form.valorPago || 0),
    defeitoRelatado: form.defeitoRelatado || "",
    observacoes: form.observacoes || "",
    itens: getOrderItems(),
  };
}

function getOrderItems() {
  return $$("#itemsList .item-row").map((row) => {
    const servico = findById(state.refs.servicos, row.querySelector(".item-service").value);
    const quantidade = Number(row.querySelector(".item-qty").value || 0);
    return servico && quantidade > 0 ? { servico, quantidade } : null;
  }).filter(Boolean);
}

function serializeOrderItems(items) {
  return items.map((item) => ({
    servico: {
      id: Number(getPath(item, "servico.id")),
      descricao: getPath(item, "servico.descricao") || "",
      valor: Number(getPath(item, "servico.valor") || 0),
    },
    quantidade: Number(item.quantidade || 0),
  })).filter((item) => item.servico.id && item.quantidade > 0);
}

function renderOrderSummary() {
  const order = getSelectedOrder();
  const itensTotal = order.itens.reduce((total, item) => total + Number(item.servico.valor || 0) * item.quantidade, 0);
  $("#orderSummary").innerHTML = `
    <div><span>Cliente</span><strong>${escapeHtml(order.cliente?.nome || "Nao selecionado")}</strong></div>
    <div><span>Tecnico</span><strong>${escapeHtml(order.tecnico?.nome || "Nao selecionado")}</strong></div>
    <div><span>Equipamento</span><strong>${escapeHtml(order.equipamento ? `${order.equipamento.marca || ""} ${order.equipamento.modelo || ""}`.trim() : "Nao selecionado")}</strong></div>
    <div><span>Servico principal</span><strong>${escapeHtml(order.servico?.descricao || "Nao selecionado")}</strong></div>
    <div><span>Itens</span><strong>${order.itens.length} item(ns) - ${money(itensTotal)}</strong></div>
    <div><span>Status inicial</span><strong>${formatStatus(order.status)}</strong></div>
    <div><span>Valor pago</span><strong>${money(order.valorPago)}</strong></div>
  `;
}

async function submitOrder(event) {
  event.preventDefault();
  if (!validateStep(state.orderStep)) return;
  const form = formToObject(new FormData(event.currentTarget));
  const payload = {
    clienteid: Number(form.clienteid),
    tecnicoid: Number(form.tecnicoid),
    equipamentoid: Number(form.equipamentoid),
    servicoid: Number(form.servicoid),
    defeitoRelatado: form.defeitoRelatado,
    observacoes: form.observacoes,
    status: form.status,
    valorPago: Number(form.valorPago || 0),
    itens: serializeOrderItems(getOrderItems()),
  };
  try {
    const editing = Boolean(state.editOrderId);
    await request(editing ? `/ordemdeservico/atualizar/${state.editOrderId}` : "/ordemdeservico", {
      method: editing ? "PUT" : "POST",
      body: payload,
    });
    event.currentTarget.reset();
    state.editOrderId = null;
    $("#itemsList").innerHTML = "";
    addOrderItem();
    renderStatusSelect();
    setOrderStep(0);
    $("#sectionEyebrow").textContent = "Ordem de servico";
    $("#sectionTitle").textContent = "Abertura de OS";
    toast(editing ? "Ordem atualizada." : "Ordem aberta.");
    await loadOrders();
  } catch (error) {
    toast(error.message);
  }
}

async function loadOrders() {
  try {
    const orders = await request("/ordemdeservico");
    state.orders = Array.isArray(orders) ? orders : [];
    renderRecentOrders(state.orders);
    renderTriage(state.orders);
  } catch (error) {
    state.orders = [];
    renderRecentOrders([]);
    renderTriage([]);
    toast(error.message);
  }
}

function renderRecentOrders(orders) {
  const target = $("#recentOrders");
  const rows = orders.slice(-6).reverse();
  target.innerHTML = rows.length ? rows.map(orderCard).join("") : '<div class="empty">Nenhuma ordem encontrada</div>';
  bindOrderActions(target);
}

function renderTriage(orders) {
  const target = $("#triageBoard");
  target.innerHTML = statuses.map((status) => {
    const rows = orders.filter((order) => order.status === status);
    return `
      <section class="triage-column">
        <h3>${formatStatus(status)} <span class="badge">${rows.length}</span></h3>
        <div class="triage-items" data-status="${status}">
          ${rows.length ? rows.map(orderCard).join("") : '<div class="empty">Vazio</div>'}
        </div>
      </section>
    `;
  }).join("");
  bindOrderActions(target);
}

function orderCard(order) {
  return `
    <article class="order-card" draggable="true" data-order-id="${order.id}">
      <strong>
        OS #${formatValue(order.id)}
        <span class="badge ${statusClass(order.status)}">${formatStatus(order.status)}</span>
      </strong>
      <p>${escapeHtml(order.defeitoRelatado || "Sem defeito informado")}</p>
      <small>${escapeHtml(getPath(order, "cliente.nome") || "Cliente nao informado")} &middot; ${money(order.valorTotalOrdem || 0)}</small>
      <div class="card-actions">
        <button type="button" class="mini-btn" data-action="edit-order" data-id="${order.id}"><span data-icon="save"></span> Editar</button>
        <button type="button" class="mini-btn" data-action="details" data-id="${order.id}"><span data-icon="eye"></span> Detalhes</button>
        <button type="button" class="mini-btn danger" data-action="cancel" data-id="${order.id}"><span data-icon="ban"></span> Cancelar</button>
      </div>
    </article>
  `;
}

function bindOrderActions(root) {
  renderIcons(root);
  root.querySelectorAll(".order-card[draggable='true']").forEach((card) => {
    card.addEventListener("dragstart", (event) => {
      event.dataTransfer.setData("text/plain", card.dataset.orderId);
      event.dataTransfer.effectAllowed = "move";
      card.classList.add("dragging");
    });
    card.addEventListener("dragend", () => card.classList.remove("dragging"));
  });
  root.querySelectorAll(".triage-items[data-status]").forEach((column) => {
    column.addEventListener("dragover", (event) => {
      event.preventDefault();
      event.dataTransfer.dropEffect = "move";
      column.classList.add("drop-target");
    });
    column.addEventListener("dragleave", () => column.classList.remove("drop-target"));
    column.addEventListener("drop", async (event) => {
      event.preventDefault();
      column.classList.remove("drop-target");
      const id = Number(event.dataTransfer.getData("text/plain"));
      await changeOrderStatus(id, column.dataset.status);
    });
  });
  root.querySelectorAll("[data-action]").forEach((button) => {
    button.addEventListener("click", () => handleOrderAction(button.dataset.action, Number(button.dataset.id)));
  });
}

async function handleOrderAction(action, id) {
  const order = state.orders.find((item) => item.id === id);
  if (!order) return;
  if (action === "edit-order") return startOrderEdit(order);
  if (action === "details") return openOrderDetails(order);
  if (action === "cancel") return cancelOrder(id);
}

async function startOrderEdit(order) {
  state.editOrderId = order.id;
  switchView("ordem");
  await loadReferences();
  const form = $("#orderForm");
  form.elements.clienteid.value = getPath(order, "cliente.id") || "";
  form.elements.tecnicoid.value = getPath(order, "tecnico.id") || "";
  form.elements.equipamentoid.value = getPath(order, "equipamento.id") || "";
  form.elements.servicoid.value = getPath(order, "servico.id") || "";
  form.elements.defeitoRelatado.value = order.defeitoRelatado || "";
  form.elements.observacoes.value = order.observacoes || "";
  form.elements.status.value = order.status || "AGUARDANDO_APROVACAO";
  form.elements.valorPago.value = order.valorPago || 0;
  $("#itemsList").innerHTML = "";
  if ((order.itens || []).length) {
    order.itens.forEach((item) => addOrderItem(item));
  } else {
    addOrderItem();
  }
  setOrderStep(0);
  $("#sectionEyebrow").textContent = "Ordem de servico";
  $("#sectionTitle").textContent = `Editar OS #${order.id}`;
  toast(`Editando OS #${order.id}.`);
}

function openOrderDetails(order) {
  $("#orderDetails").innerHTML = `
    <div class="details-grid">
      <div><span>Cliente</span><strong>${escapeHtml(getPath(order, "cliente.nome") || "-")}</strong></div>
      <div><span>Tecnico</span><strong>${escapeHtml(getPath(order, "tecnico.nome") || "-")}</strong></div>
      <div><span>Equipamento</span><strong>${escapeHtml(`${getPath(order, "equipamento.marca") || ""} ${getPath(order, "equipamento.modelo") || ""}`.trim() || "-")}</strong></div>
      <div><span>Status</span><strong>${formatStatus(order.status)}</strong></div>
      <div><span>Abertura</span><strong>${formatDate(order.dataAbertura)}</strong></div>
      <div><span>Finalizacao</span><strong>${formatDate(order.dataFinalizacao)}</strong></div>
      <div><span>Total</span><strong>${money(order.valorTotalOrdem || 0)}</strong></div>
      <div><span>Pago</span><strong>${money(order.valorPago || 0)}</strong></div>
    </div>
    <section>
      <h3>Defeito relatado</h3>
      <p>${escapeHtml(order.defeitoRelatado || "-")}</p>
    </section>
    <section>
      <h3>Observacoes</h3>
      <p>${escapeHtml(order.observacoes || "-")}</p>
    </section>
    <section>
      <h3>Itens</h3>
      ${(order.itens || []).length ? `<ul>${order.itens.map((item) => `<li>${escapeHtml(getPath(item, "servico.descricao") || "Servico")} x ${item.quantidade} - ${money(item.valortot)}</li>`).join("")}</ul>` : "<p>Nenhum item adicional.</p>"}
    </section>
  `;
  $("#orderDialog").showModal();
}

async function cancelOrder(id) {
  try {
    await request(`/ordemdeservico/cancelar/${id}`, { method: "PUT" });
    toast(`OS #${id} cancelada.`);
    await loadOrders();
  } catch (error) {
    toast(error.message);
  }
}

async function changeOrderStatus(id, status) {
  const order = state.orders.find((item) => item.id === id);
  if (!order || order.status === status) return;
  try {
    await request(`/ordemdeservico/atualizar/${id}`, {
      method: "PUT",
      body: orderPayloadFromResponse(order, status),
    });
    toast(`OS #${id} movida para ${formatStatus(status)}.`);
    await loadOrders();
  } catch (error) {
    toast(error.message);
  }
}

function orderPayloadFromResponse(order, status) {
  return {
    clienteid: getPath(order, "cliente.id"),
    tecnicoid: getPath(order, "tecnico.id"),
    equipamentoid: getPath(order, "equipamento.id"),
    servicoid: getPath(order, "servico.id"),
    defeitoRelatado: order.defeitoRelatado,
    observacoes: order.observacoes,
    status,
    valorPago: order.valorPago || 0,
    itens: serializeOrderItems(order.itens || []),
  };
}

async function request(path, options = {}) {
  const base = state.apiBase.replace(/\/$/, "");
  const response = await fetch(`${base}${path}`, {
    method: options.method || "GET",
    headers: {
      "Content-Type": "application/json",
      ...(options.auth === false || !state.token ? {} : { Authorization: `Bearer ${state.token}` }),
    },
    body: options.body ? JSON.stringify(options.body) : undefined,
  });
  const text = await response.text();
  if (!response.ok) {
    let message = text || `Erro ${response.status} ao acessar ${path}`;
    if (text) {
      try {
        const errorBody = JSON.parse(text);
        message = errorBody.mensagem || errorBody.message || errorBody.erro || message;
      } catch (error) {
        message = text;
      }
    }
    if (response.status === 401 || response.status === 403) throw new Error("Acesso negado. Confira login e perfil.");
    throw new Error(message);
  }
  return text ? JSON.parse(text) : null;
}

function formToObject(formData) {
  return Object.fromEntries(Array.from(formData.entries()).map(([key, value]) => [key, value === "true" ? true : value === "false" ? false : value]));
}

function findById(rows, id) {
  return rows.find((item) => String(item.id) === String(id));
}

function getPath(object, path) {
  return path.split(".").reduce((acc, key) => acc && acc[key], object);
}

function formatValue(value) {
  if (value === null || value === undefined || value === "") return "-";
  if (typeof value === "boolean") return value ? "Sim" : "Nao";
  if (typeof value === "number" && !Number.isInteger(value)) return money(value);
  return escapeHtml(String(value));
}

function money(value) {
  return Number(value || 0).toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
}

function formatDate(value) {
  return value ? new Date(value).toLocaleString("pt-BR") : "-";
}

function formatStatus(status) {
  return String(status || "").replaceAll("_", " ").toLowerCase().replace(/\b\w/g, (letter) => letter.toUpperCase());
}

function statusClass(status) {
  if (["FINALIZADA", "FATURADA", "ENTREGUE"].includes(status)) return "success";
  if (["CANCELADA"].includes(status)) return "danger";
  if (["AGUARDANDO_APROVACAO", "AGUARDANDO_PECA"].includes(status)) return "warning";
  return "";
}

function headerLabel(key) {
  return key.split(".").pop().replace(/([A-Z])/g, " $1");
}

function escapeHtml(value) {
  return String(value).replace(/[&<>"']/g, (char) => ({
    "&": "&amp;",
    "<": "&lt;",
    ">": "&gt;",
    '"': "&quot;",
    "'": "&#039;",
  }[char]));
}

let toastTimer;
function toast(message) {
  const el = $("#toast");
  el.textContent = message;
  el.classList.add("show");
  clearTimeout(toastTimer);
  toastTimer = setTimeout(() => el.classList.remove("show"), 3200);
}

init();
