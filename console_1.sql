select *
from cursovaya.tabi;

CREATE OR REPLACE FUNCTION add_to_log() RETURNS TRIGGER AS
$$
DECLARE
    mstr varchar(30);
BEGIN
    insert into cursovaya.tabi (id, name) values (6, 'tffrigi');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER t_user
    AFTER INSERT
    ON cursovaya.test2
    FOR EACH ROW
EXECUTE PROCEDURE add_to_log();

drop trigger t_user on cursovaya.test2;

select *
from cursovaya.test2;

insert into cursovaya.test2
values (7, 'igor');

--Изменяется количество заказов, если заказ оформлен --Изменяется количество заказов, если заказ оформлен --Изменяется количество заказов, если заказ оформлен
CREATE TRIGGER t_user2
    AFTER INSERT
    ON cursovaya.final_order
    FOR EACH ROW
EXECUTE PROCEDURE add_to_log2();

drop trigger t_user2 on cursovaya.final_order;


CREATE OR REPLACE FUNCTION add_to_log2() RETURNS TRIGGER AS
$$
DECLARE
    mstr   varchar(30);
    idd    bigint;
    cn     int;
    cn2    int;
    cnt    int;
    curcnt int;
    fprice double precision;
    drate  double precision; -- коэф скидки 1 - целая стоимость, 0.9 скидка 10 процентов
    finstr varchar(30);
BEGIN

    cn := cursovaya.requests.basket_id from cursovaya.requests order by id desc limit 1; --id user's
    cn2 := cursovaya.requests.final_order_id from cursovaya.requests order by id desc limit 1;
    --id users

--добавить это значение к счётчику заказов
--     cnt := sum(cursovaya.requests.number_of_days) from cursovaya.requests where cursovaya.requests.basket_id = cn;

    --order_amount - заменить на общую сумму
--     if (select usr.order_amount from cursovaya."user" as usr where usr.id = cn) is null then
--         fprice = 0;
--     else

--     end if;

    fprice := sum(fo.final_price) from cursovaya.final_order as fo where fo.user_id = cn;

    drate = 1.0;

    if (fprice > 500 and fprice < 2000)
    then
        drate = 0.9;
    end if;
    if (fprice >= 2000 and fprice < 5000)
    then
        drate = 0.8;
    end if;
    if (fprice >= 5000)
    then
        drate = 0.7;
    end if;

    update cursovaya."user"
    set order_amount=fprice
    where cursovaya."user".id = cn;

    update cursovaya."user"
    set discount_rate=drate
    where cursovaya."user".id = cn;

    mstr = concat('sum: ', cn);
    mstr = concat(mstr, '  ');
    mstr = concat(mstr, fprice);

    insert into cursovaya.tabi (id, name) values (NEW.id, mstr);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
--Изменяется количество заказов, если заказ оформлен --Изменяется количество заказов, если заказ оформлен --Изменяется количество заказов, если заказ оформлен
select * from user_mappings;

SELECT * FROM pg_catalog.pg_tables;


SELECT * FROM public.user_roles;

SELECT relname, relpages FROM pg_class;
--Работает удаление платформы --Работает удаление платформы --Работает удаление платформы --Работает удаление платформы --Работает удаление платформы
drop trigger dp_platform on cursovaya.platforms;

drop function get_all_foo();

CREATE TRIGGER dp_platform
    before delete or update
    ON cursovaya.platforms
    FOR EACH ROW
EXECUTE PROCEDURE get_all_foo();


CREATE OR REPLACE FUNCTION get_all_foo() RETURNS TRIGGER AS
$$
DECLARE
    r cursovaya.products%rowtype;
BEGIN
    FOR r IN
        SELECT * FROM cursovaya.products WHERE id > 0
        LOOP
            if r.platforms_id = old.id then
                update cursovaya.products set platforms_id = 9 where id = r.id;
                update cursovaya.products set idd = 9 where id = r.id;
            end if;
        END LOOP;
    RETURN old;
END;
$$ LANGUAGE plpgsql;
--Работает удаление платформы --Работает удаление платформы --Работает удаление платформы --Работает удаление платформы --Работает удаление платформы


select *
from cursovaya.platforms;


--удоляет
delete
FROM cursovaya.platforms
where cursovaya.platforms.id = 5;

delete
from cursovaya.imagest
where id = 7;
--удоляет


SELECT *
FROM get_all_foo(3);