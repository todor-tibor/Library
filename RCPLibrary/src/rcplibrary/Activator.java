package rcplibrary;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.edu.library.IAuthorService;

public class Activator implements BundleActivator {

	private BundleContext context;

	// /*
	// * (non-Javadoc)
	// * @see
	// org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	// */
	// public void start(BundleContext bundleContext) throws Exception {
	// Activator.context = bundleContext;
	// }
	//
	// /*
	// * (non-Javadoc)
	// * @see
	// org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	// */
	// public void stop(BundleContext bundleContext) throws Exception {
	// Activator.context = null;
	// }
	//
	// package com.github.marschall.jboss.osgi.remoting.ejb.sample.client;
	//
	// import java.util.Queue;
	// import java.util.concurrent.LinkedBlockingQueue;
	// import java.util.concurrent.TimeUnit;
	//
	// import org.osgi.framework.BundleActivator;
	// import org.osgi.framework.BundleContext;
	// import org.osgi.framework.Filter;
	// import org.osgi.framework.InvalidSyntaxException;
	// import org.osgi.framework.ServiceReference;
	// import org.osgi.util.tracker.ServiceTracker;
	// import org.osgi.util.tracker.ServiceTrackerCustomizer;
	//
	// import
	// com.github.marschall.jboss.osgi.remoting.ejb.sample.StatefulRemote1;
	// import
	// com.github.marschall.jboss.osgi.remoting.ejb.sample.StatefulRemote2;
	// import
	// com.github.marschall.jboss.osgi.remoting.ejb.sample.IAuthorService;
	// import
	// com.github.marschall.jboss.osgi.remoting.ejb.sample.StatelessRemote2;
	//
	// public class Activator implements BundleActivator {

	private volatile Queue<ServiceTracker<?, ?>> trackers;

	private ServiceTracker<IAuthorService, IAuthorService> serviceTracker;

	@Override
	public void start(final BundleContext context) throws Exception {
		this.context = context;
		this.trackers = new LinkedBlockingQueue<ServiceTracker<?, ?>>();

		this.serviceTracker = new ServiceTracker<IAuthorService, IAuthorService>(context, IAuthorService.class,
				new WaitForProxies());
		this.serviceTracker.open(true);
	}

	void callServices() {
		IAuthorService authorService = this.lookup(IAuthorService.class);
		System.out.println(authorService.getClass().getSimpleName());
		System.out.println(authorService.getById("1"));
	}

	final class WaitForProxies implements ServiceTrackerCustomizer<IAuthorService, IAuthorService> {

		@Override
		public IAuthorService addingService(final ServiceReference<IAuthorService> reference) {
			IAuthorService service = Activator.this.context.getService(reference);
			// we are inside the callback that's executed when a service is
			// registered
			// in this thread we can not wait for services being registered
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					callServices();
				}
			};
			Thread thread = new Thread(runnable, "service-caller");
			thread.start();
			return service;
		}

		@Override
		public void modifiedService(final ServiceReference<IAuthorService> reference, final IAuthorService service) {
			// nothing
		}

		@Override
		public void removedService(final ServiceReference<IAuthorService> reference, final IAuthorService service) {
			Activator.this.context.ungetService(reference);
		}

	}

	private <T> T lookup(final Class<T> clazz) {
		String filterString = "(&(objectClass=" + clazz.getName() + ")(service.imported=*))";
		Filter filter;
		try {
			filter = this.context.createFilter(filterString);
		} catch (InvalidSyntaxException e1) {

			throw new RuntimeException("invalid filter: " + filterString);
		}
		ServiceTracker<T, T> tracker = new ServiceTracker<T, T>(this.context, filter, null);
		tracker.open(true);
		this.trackers.add(tracker);
		T service = tracker.getService();
		if (service != null) {
			return service;
		} else {
			try {
				service = tracker.waitForService(TimeUnit.SECONDS.toMillis(1L));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new IllegalArgumentException("interrupted while waiting for: " + clazz);
			}
			if (service == null) {
				throw new IllegalArgumentException("service not found: " + clazz);
			}
			return service;
		}
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		ServiceTracker<?, ?> tracker = this.trackers.poll();
		while (tracker != null) {
			tracker.close();
			tracker = this.trackers.poll();
		}
		this.serviceTracker.close();
		this.serviceTracker = null;
		this.trackers = null;

	}

}
