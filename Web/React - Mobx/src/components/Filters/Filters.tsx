import React, { Component } from 'react';
import store from '../../stores/stores';
import { observer } from 'mobx-react';
import '../Filters/Filters.css';

@observer
class Filters extends Component {

    constructor(props: {}){
        super(props);

        store.getCategories();
    }

    render() {
        return <div className='Filterscont'>
        <h3 className='Filterscont__tituloDeps'>{store.departments ? 'Departamentos' : 'Loading departments'}</h3>
        {   /*
                si store.departments existe lo recorre y por cada elemento dentro de ese array crea un div con el nombre que tiene ese objeto (dep.name), cada vez que se le de click a uno de estos divs creados se setea una variable en store como el departamento seleccionado actualmente y asi mismo le pone una clase distinta para que se vea diferente
            */
            store.departments && store.departments.map((dep) => {
                return <div className={store.currentDept == dep.department_id? "Filterscont__tituloDeps__dep__active" : "Filterscont__tituloDeps__dep"} key={dep.department_id}
                onClick={() =>{
                    store.setDepartment(dep.department_id);
                }}>
                {dep.name}
            </div>;
            })
        }

        <h3 className='Filterscont__tituloCats'>{store.categories ? 'Categorias' : 'Loading categories'}</h3>
        { //pasa lo mismo de aariba pero con las categorias
            store.categories && store.categories.map((cat) => {
                return cat.department_id == store.currentDept && <div key={cat.category_id} className={store.currentCat == cat.category_id? "Filterscont__tituloCats__cat__active" : "Filterscont__tituloCats__cat"}
                    onClick={() =>{
                        store.setCategorie(cat.category_id);
                    }}>
                    {cat.name}  
                </div>
            })
        }
    </div>
    }
}

export default Filters;