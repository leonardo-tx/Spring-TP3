package com.example.project.core.supplier.factory;

import com.example.project.core.supplier.model.Supplier;

public interface SupplierFactory {
    Supplier create(SupplierParams params);
}
