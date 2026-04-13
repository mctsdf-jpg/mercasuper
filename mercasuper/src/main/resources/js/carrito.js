const { useState, useEffect } = React;

function App() {
    const [productos, setProductos] = useState([]);
    const [carrito, setCarrito] = useState([]);

    useEffect(() => {
        fetch('/api/productos')
            .then(res => res.json())
            .then(data => setProductos(data))
            .catch(err => console.error("Error cargando API:", err));
    }, []);

    const agregar = (p) => {
        const existe = carrito.find(x => x.id === p.id);
        if (existe) {
            setCarrito(carrito.map(x => x.id === p.id ? {...x, cant: x.cant + 1} : x));
        } else {
            setCarrito([...carrito, {...p, cant: 1}]);
        }
    };

    const total = carrito.reduce((acc, item) => acc + (item.precio * item.cant), 0);

    return (
        <div className="container mt-5">
            <h2 className="mb-4">Módulo de Ventas (React JS)</h2>
            <div className="row">
                <div className="col-md-8">
                    <div className="row">
                        {productos.map(p => (
                            <div className="col-md-4 mb-3" key={p.id}>
                                <div className="card h-100 shadow-sm">
                                    <img src={`/img/${p.imagen}`} className="card-img-top" style={{height: '120px', objectFit: 'contain', padding: '10px'}} alt={p.nombre}/>
                                    <div className="card-body text-center">
                                        <h5>{p.nombre}</h5>
                                        <p className="text-success fw-bold">${p.precio}</p>
                                        <button onClick={() => agregar(p)} className="btn btn-danger btn-sm w-100">Agregar</button>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
                <div className="col-md-4">
                    <div className="card shadow">
                        <div className="card-body">
                            <h4>Carrito de Compras</h4>
                            <hr/>
                            {carrito.map(item => (
                                <div key={item.id} className="d-flex justify-content-between mb-2">
                                    <span>{item.nombre} (x{item.cant})</span>
                                    <span>${item.precio * item.cant}</span>
                                </div>
                            ))}
                            <hr/>
                            <h5 className="text-end">Total: ${total}</h5>
                            <button className="btn btn-success w-100 mt-3" onClick={() => alert('Venta registrada con éxito')}>Finalizar</button>
                            <a href="/tienda" className="btn btn-link w-100 mt-2 text-decoration-none">Volver a la tienda</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<App />);