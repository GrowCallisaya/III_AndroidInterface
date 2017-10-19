
/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.material.demo.codelab.buildingbeautifulapps;

/**
 * A product entry in the list of json_productos.
 */
public class Producto {
    public final String titulo;
    public final String url;
    public final String precio;
    public final String descripcion;

    public Producto(String titulo, String url, String price, String descripcion) {
        this.titulo = titulo;
        this.url = url;
        this.precio = price;
        this.descripcion = descripcion;
    }
}
