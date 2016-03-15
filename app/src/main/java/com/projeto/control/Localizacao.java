package com.projeto.control;

import java.util.Map;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projeto.model.*;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

public class Localizacao implements LocationListener {

	private LatLng coordenadas;
	private Context ctx;
	private LocationManager locationManager;
	// Flag de controle para dar o zoom automático no momento que carregar novas
	// coordenadas
	private boolean zoomAutomatico;
	private GoogleMap mapa;

	public Localizacao(Context ctx) {
		// TODO Auto-generated constructor stub
		this.ctx = ctx;
		this.locationManager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
	}

	public Localizacao(Context ctx, boolean zoom, GoogleMap map) {
		// TODO Auto-generated constructor stub
		this(ctx);
		this.zoomAutomatico = zoom;
		this.mapa = map;
	}

	public LatLng getCoordenadas() {
		return this.coordenadas;
	}

	public boolean IsEnabled() {
		return this.locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}

	public void ligarGPS() {
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		ctx.startActivity(intent);

	}

	public void ativarLocalizacao() {
		// Verifica se o provider está ativo está ativo
		// Ativo caso esteja desativado
		if (!this.IsEnabled()) {
			this.ligarGPS();
		}
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				5000, 10, this);
	}

	public void desativarLocalizacao() {
		// Verifica se o provider está ativo está ativo
		// Desativo caso esteja ativado
		if (this.IsEnabled()) {
			locationManager.removeUpdates(this);
		}
	}

	public Marker marcarLocalizacao(GoogleMap map, double latitude,
			double longitude, String titulo) {
		LatLng coordenadasMap = new LatLng(latitude, longitude);
		Marker frameworkSystem = map.addMarker(new MarkerOptions().position(
				coordenadasMap).title(titulo));
		frameworkSystem.setSnippet("Clique para agendar");
		return frameworkSystem;
	}

	public void zoomMap(GoogleMap map) {
		// Move a câmera para as coordenadas da classe
		 Criteria criteria = new Criteria();
         Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
         if (location != null)
         {
             map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                     new LatLng(location.getLatitude(), location.getLongitude()), 13));

             CameraPosition cameraPosition = new CameraPosition.Builder()
             .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
             .zoom(15)                   // Sets the zoom
             .bearing(90)                // Sets the orientation of the camera to east
             .tilt(40)                   // Sets the tilt of the camera to 30 degrees
             .build();                   // Creates a CameraPosition from the builder
         map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

         }
 		 
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		if (location != null) {
			this.coordenadas = new LatLng(location.getLatitude(),
					location.getLongitude());
			// Se o zoom automático está habilitado, do o zoom
			if (this.zoomAutomatico) {
				this.zoomMap(mapa);
				//Marca a posição do celular no mapa
				this.mapa.getUiSettings().setMyLocationButtonEnabled(true);
		        this.mapa.getUiSettings().setCompassEnabled(true); 
		        this.mapa.setMyLocationEnabled(true);
				this.zoomAutomatico = false; //Seto como false para so executar uma vez
				
			}

		}

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

}
