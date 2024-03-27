package com.example.doanmobile.dangsanpham;

public class ProductManager {
    private  PrototypeProduct _prototype;

    public ProductManager(PrototypeProduct prototype)
    {
        _prototype = prototype;
    }

    public PrototypeProduct createProduct()
    {
        return _prototype.clone();
    }


}
