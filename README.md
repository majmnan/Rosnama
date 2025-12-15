# 📅 Rosnama (رزنامة السعودية)

A smart Saudi events platform to discover, manage, request, publish, and register for events with AI-powered recommendations and summaries.

---
## 📌 Team Responsibilities Recap

| Member       | Core Responsibilities                                                        |
| ------------ | ---------------------------------------------------------------------------- |
| Ibrahim  | User, Category, Internal Events, Scheduling, WhatsApp connection             |
| Asrar    | Admin, Reviews, External Events, AI summaries, Email                         |
| Mohannad | Filters, Requests, Registration, Serp API, AI recommendations, Notifications |

---

## 👤 Admin (/api/v1/admin)

By: Asrar

| Method | Path | Description | Name |
|---|---|---|---|
| GET | /get | Get all admins | Asrar |
| POST | /add | Add admin | Asrar |
| PUT | /update/{id} | Update admin | Asrar |
| DELETE | /delete/{id} | Delete admin | Asrar |

---

## 👤 User (/api/v1/user)

By: Ibrahim

| Method | Path | Description | Name |
|---|---|---|---|
| GET | /get | Get all users | Ibrahim |
| POST | /add | Add user | Ibrahim |
| PUT | /update/{id} | Update user | Ibrahim |
| PUT | /add-balance/{id}/{balance} | Add balance to user | Ibrahim |
| DELETE | /delete/{id} | Delete user | Ibrahim |

---

## 🗂 Category (/api/v1/category)

By: Ibrahim

| Method | Path | Description | Name |
|---|---|---|---|
| GET | /get | Get all categories | Ibrahim |
| POST | /add/{adminId} | Add category (admin) | Ibrahim |
| PUT | /update/{adminId}/{categoryId} | Update category (admin) | Ibrahim |
| DELETE | /delete/{adminId}/{categoryId} | Delete category (admin) | Ibrahim |

---

## 🏢 EventOwner (/api/v1/event-owner)

By: Ibrahim

| Method | Path | Description | Name |
|---|---|---|---|
| GET | /get | Get all event owners | Ibrahim |
| POST | /add | Add event owner | Ibrahim |
| PUT | /update/{id} | Update event owner | Ibrahim |
| DELETE | /delete/{id} | Delete event owner | Ibrahim |

---

---

## 📝 InternalEventRequest

By: Ibrahim, 
Enhanced by: Mohannad

| Method | Path | Description | Name |
|---|---|---|---|
| GET | /get/{adminId} | Get all internal event requests (admin) | Mohannad |
| PUT | /offer-for/{requestId}/{price} | Admin offers price | Mohannad |
| PUT | /negotiates/{ownerId}/{requestId}/{price} | Owner negotiates | Mohannad |
| PUT | /accept-pay/{ownerId}/{requestId} | Owner accepts & pays | Mohannad |
| DELETE | /reject/{adminId}/{requestId} | Admin rejects the request | Mohannad |

---

## 🏠 InternalEvent (/api/v1/internal-event)

By: Ibrahim, Mohannad, Asrar

| Method | Path | Description | Name |
|---|---|---|---|
| GET | /get | Get all internal events | Ibrahim |
| POST | /add/{eventOwnerId}/{adminId} | Add internal event by owner | Ibrahim |
| PUT | /update/{ownerId}/{eventId} | Update internal event by owner | Ibrahim |
| DELETE | /delete/{ownerId}/{internalEventId} | Delete internal event by owner | Ibrahim |
| GET | /type/{type} | Get internal events by type | Mohannad |
| GET | /city/{city} | Get internal events by city | Mohannad |
| GET | /between/{after}/{before} | Get internal events between dates | Mohannad |
| GET | /get-category/{categoryId} | Get ongoing internal events by category | Mohannad |
| GET | /recommend-user-attending/{userId} | Recommend (based on attended events) | Mohannad |
| GET | /recommend-user-high-rate/{userId} | Recommend (based on high-rated events) | Mohannad |
| GET | /recommend-event/{eventId} | Recommend (based on event) | Mohannad |
| GET | /review-summary/{ownerId}/{eventId} | AI review summary for owner | Asrar |
| GET | /review-summary/{eventId} | AI review summary for user | Asrar |

---

## 📤 ExternalEventRequest (/api/v1/external-event-request)

By: Mohannad

| Method | Path | Description | Name |
|---|---|---|---|
| GET | /get/{adminId} | Get external event requests (admin) | Mohannad |
| PUT | /{adminId}/offer-for/{requestId}/{price} | Admin offers price | Mohannad |
| PUT | /negotiates/{ownerId}/{requestId}/{price} | Owner negotiates | Mohannad |
| PUT | /accept-pay/{ownerId}/{requestId} | Owner accepts & pays | Mohannad |
| DELETE | /reject/{adminId}/{requestId} | Admin rejects the request | Mohannad |

---

## 🌍 ExternalEvent (/api/v1/external-event)

By: Asrar, Mohannad

| Method | Path | Description | Name |
|---|---|---|---|
| GET | /get/{adminId} | Get all external events (admin) | Asrar |
| POST | /add/{adminId} | Add external event (admin) | Asrar |
| PUT | /{adminId}/update/{id} | Update external event (admin) | Asrar |
| DELETE | /{adminId}/delete/{id} | Delete external event (admin) | Asrar |
| POST | /request-event | Owner creates external event + request auto generated | Asrar |
| PUT | /owner/{ownerId}/update/{eventId} | Update external event (owner) | Asrar |
| DELETE | /owner/{ownerId}/delete/{eventId} | Delete external event (owner) | Asrar |
| GET | /upcoming | Get upcoming external events | Mohannad |
| GET | /ongoing | Get ongoing external events | Mohannad |
| GET | /between/{after}/{before} | Get ongoing external events between dates | Mohannad |
| GET | /type/{type} | Get events by type | Mohannad |
| GET | /city/{city} | Get events by city | Mohannad |
| GET | /category/{categoryId} | Get ongoing events by category | Mohannad |
| GET | /recommend-event/{eventId} | Recommend (based on event) | Mohannad |
| GET | /recommend-user-attending/{userId} | Recommend (based on attended events) | Mohannad |
| GET | /recommend-user-likes/{userId} | Recommend (based on high-rated events) | Mohannad |

---

## 🧾 Registration (/api/v1/registration)

By: Mohannad

| Method | Path | Description | Name |
|---|---|---|---|
| POST | /add | Add registration | Mohannad |
| PUT | /use/{registrationId} | Use registration | Mohannad |
| GET | /get-by-event-date/{internalEventId}/{date} | Get registrations of an event in a specific day | Mohannad |
| GET | /reg-by-user/{userId} | Get registrations by user | Mohannad |

---

## ⭐️ Review (/api/v1/review)

By: Asrar

| Method | Path | Description | Name |
|---|---|---|---|
| GET | /get/{eventId} | Get all reviews of internal event | Asrar |
| POST | /add | Add review | Asrar |
| PUT | /update/{reviewId} | Update review | Asrar |
| DELETE | /delete/{reviewId} | Delete review | Asrar |

---

## 🔧 Integrations & Background Services – Responsibilities Breakdown

---

### 📲 WhatsApp

* **Connection:** Ibrahim
* **Notification usage & enhancement:** Mohannad

---

### 📧 Email

* **Connection & setup:** Asrar
* **Notification logic & enhancements:** Mohannad

---

### ⏱️ Schedule (Daily Jobs)

* **Daily status changes (events & registrations):** Ibrahim
* **Enhanced scheduling logic & extra tasks:** Mohannad

---

### 🌐 SerpApi (Google Events)

* **SerpApi service implementation:** Mohannad
* **Scheduled fetching & data integration:** Mohannad


