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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * MainActivity que muestra la lista de Productos disponibles.
 * @author Grow Callisaya
 * @supported by Google Material Design Components (MDC)
 *
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ProductoAdapter adaptador;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.su_main);



        //Toolbar (Barra Blanca)
        Toolbar appBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(appBar);






        //Instanciamos lista de Productos y ImageRequester
        ArrayList<Producto> lista_productos = leerListaProductos();
        ImageRequester imageRequester = ImageRequester.getInstance(this);




        // Añade este Código
        Producto productoCabecera = getProductoCabecera(lista_productos);
        NetworkImageView headerImage = (NetworkImageView) findViewById(R.id.app_bar_image);
        imageRequester.setImageFromUrl(headerImage, productoCabecera.url);
        

        //Listamos los productos en RecyclerView
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.product_list);
        recyclerView.setHasFixedSize(true);




        //Creamos la Lista en Columnas (Grids)
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        adaptador = new ProductoAdapter(lista_productos, imageRequester);
        recyclerView.setAdapter(adaptador);





        final BottomNavigationView menuAbajo =
                (BottomNavigationView) findViewById(R.id.bottom_navigation);

        Button boton = (Button) findViewById(R.id.accion);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuAbajo.setVisibility(View.VISIBLE);
            }
        });

        menuAbajo.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        LinearLayoutManager layoutManager =
                                (LinearLayoutManager) recyclerView.getLayoutManager();
                        layoutManager.scrollToPositionWithOffset(0, 0);

                        barajarProductos();
                        return true;
                    }
                });

    }

    private void barajarProductos() {
        Toast.makeText(this, "Hola me cambiaste!!!!!!!!", Toast.LENGTH_SHORT).show();

    }

    // Añade el Método getProductoCabecera
    private Producto getProductoCabecera(ArrayList<Producto> lista_productos) {
            if (lista_productos.size() == 0) {
                throw new IllegalArgumentException("Tiene que haber por lo menos un producto");
            }

            for (int i = 0; i < lista_productos.size(); i++) {
                if ("Balón de Playa".equals(lista_productos.get(i).titulo)) {
                    return lista_productos.get(i);
                }
            }
            return lista_productos.get(0);
    }

    private void TeEmociones() {
        Toast.makeText(this, "Hola soy Diego, y etoy emocionado!!!!!!! XD ", Toast.LENGTH_SHORT).show();
    }


    /**
     * @method
     * Metodo para obtener una lista desde un JSON
     *
     * **/
    private ArrayList<Producto> leerListaProductos() {
        InputStream inputStream = getResources().openRawResource(R.raw.json_productos);
        Type productoType = new TypeToken<ArrayList<Producto>>() {}.getType();
        try {
            return JsonReader.readJsonStream(inputStream, productoType);
        } catch (IOException e) {
            Log.e(TAG, "Error al leer lista de productos JSON ", e);
            return new ArrayList<>();
        }
    }









    /**
     * @class static
     * Adaptador de Productos, que es un RecyclerView (Lista)
     *
     * **/
    private static final class ProductoAdapter extends RecyclerView.Adapter<ProductoViewHolder> {
        private List<Producto> lista_productos;
        private final ImageRequester imageRequester;

        //Constructor
        ProductoAdapter(List<Producto> lista_productos, ImageRequester imageRequester) {
            this.lista_productos = lista_productos;
            this.imageRequester = imageRequester;
        }


        //Crea la Nueva Vista
        @Override
        public ProductoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ProductoViewHolder(viewGroup);
        }


        //Reemplaza el contenido de una vista
        @Override
        public void onBindViewHolder(ProductoViewHolder viewHolder, int i) {
            viewHolder.bind(lista_productos.get(i), imageRequester);
        }


        // Retorna el tamaño de tu lista
        @Override
        public int getItemCount() {
            return lista_productos.size();
        }
    }









    /**
     * @class static
     * Proveedor de la vista de Producto (Modelo)
     *
     * **/
    private static final class ProductoViewHolder extends RecyclerView.ViewHolder {
        private final NetworkImageView imageView;
        private final TextView precioView;

        //Constructor
        ProductoViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.su_producto, parent, false));
            imageView = (NetworkImageView) itemView.findViewById(R.id.image);
            precioView = (TextView) itemView.findViewById(R.id.price);
            itemView.setOnClickListener(clickListener);
        }

        //Al hacer clic en un item
        private final View.OnClickListener clickListener =
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Producto producto = (Producto) v.getTag(R.id.tag_product_entry);
                        // TODO: Mostrar detalles de Producto


                    }
                };

        //Cuando se crea la vista
        void bind(Producto producto, ImageRequester imageRequester) {
            itemView.setTag(R.id.tag_product_entry, producto);
            imageRequester.setImageFromUrl(imageView, producto.url);
            precioView.setText(producto.precio);
        }
    }
}
