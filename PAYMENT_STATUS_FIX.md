# Payment Status Fix - Issue & Resolution

## Issue Summary
Payment status was always showing as **PENDING** and could never be updated to **SUCCESS** or **FAILED**.

## Root Cause Analysis
The payment system had three critical issues:

1. **No status field in PaymentRequest DTO** - Clients couldn't send status information when updating payments
2. **updatePayment method didn't handle status** - The update endpoint only updated amount, paymentMode, and transactionId, but ignored status
3. **No dedicated endpoint for status changes** - There was no way to specifically mark a payment as successful or failed

## Changes Made

### 1. Updated PaymentRequest DTO
**File:** `payment-service/src/main/java/com/institute/payment/dto/PaymentRequest.java`

✅ Added `PaymentStatus status` field to allow clients to include status in update requests

```java
private PaymentStatus status;  // Added - optional field for status updates
```

### 2. Enhanced updatePayment Method
**File:** `payment-service/src/main/java/com/institute/payment/service/impl/PaymentServiceImpl.java`

✅ Modified to check if status is included in the request and update it

```java
// Update status if provided in the request
if (request.getStatus() != null) {
    payment.setStatus(request.getStatus());
}
```

### 3. Added New Service Method
**File:** `payment-service/src/main/java/com/institute/payment/service/PaymentService.java`

✅ Added `markPaymentStatus()` method for dedicated status updates

```java
PaymentResponse markPaymentStatus(Long id, PaymentStatus status);
```

### 4. Implemented markPaymentStatus Method
**File:** `payment-service/src/main/java/com/institute/payment/service/impl/PaymentServiceImpl.java`

✅ Implementation to update payment status

```java
@Override
public PaymentResponse markPaymentStatus(Long id, PaymentStatus status) {
    Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
    
    payment.setStatus(status);
    paymentRepository.save(payment);
    
    return mapToResponse(payment);
}
```

### 5. Added New REST Endpoint
**File:** `payment-service/src/main/java/com/institute/payment/controller/PaymentController.java`

✅ New PATCH endpoint to mark payment status

```java
@PatchMapping("/{id}/status")
@PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
public PaymentResponse markPaymentStatus(
        @PathVariable Long id,
        @RequestParam PaymentStatus status) {
    return paymentService.markPaymentStatus(id, status);
}
```

## How to Use

### Option 1: Update Payment with Status (Any field)
```bash
PUT /api/payment/{id}
Content-Type: application/json

{
    "paymentCode": "PAY-2001",
    "studentCode": "STU-1002",
    "courseCode": "C-1001122",
    "amount": 15000,
    "paymentMode": "UPI",
    "transactionId": "TXN-111111",
    "status": "SUCCESS"
}
```

### Option 2: Mark Payment Status (Dedicated Endpoint)
```bash
PATCH /api/payment/{id}/status?status=SUCCESS
```

### Status Values
- `PENDING` - Initial status when payment is created
- `SUCCESS` - Payment processed successfully
- `FAILED` - Payment processing failed

## Example Workflow

1. **Create Payment** → Status automatically set to PENDING
   ```bash
   POST /api/payment
   ```

2. **Update Payment Status to SUCCESS** → Use the new PATCH endpoint
   ```bash
   PATCH /api/payment/1/status?status=SUCCESS
   ```

3. **Verify Payment** → Get payment details
   ```bash
   GET /api/payment/1
   ```

## Testing Scenario

Using your example:
```json
{
  "id": 3,
  "paymentCode": "PAY-2001",
  "studentCode": "STU-1002",
  "courseCode": "C-1001122",
  "amount": 15000,
  "paymentMode": "UPI",
  "transactionId": "TXN-111111",
  "paymentDate": "2026-04-26",
  "status": "PENDING"
}
```

**Before Fix:** Status would always remain PENDING ❌
**After Fix:** Status can be updated using either:
- `PATCH /api/payment/3/status?status=SUCCESS` ✅
- `PUT /api/payment/3` with `"status": "SUCCESS"` in body ✅

## Database Note
The Payment entity uses `@Enumerated(EnumType.STRING)` for status, so the database stores the status as text ("PENDING", "SUCCESS", "FAILED").

