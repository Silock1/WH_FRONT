package com.warehouse_accounting.exceptions;

public class WarehouseCreatingProductDtoException extends RuntimeException {
    public WarehouseCreatingProductDtoException(String message, Throwable cause) { super(message, cause); }
    public WarehouseCreatingProductDtoException(String message) { super(message); }
    public WarehouseCreatingProductDtoException(Throwable cause) { super(cause); }
}
