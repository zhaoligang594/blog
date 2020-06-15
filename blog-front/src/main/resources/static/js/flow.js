function flowjs(){


    const container = document.querySelector('.inner_zlg_container');

    // $(container).mouseover(function () {
    //     alert(123);
    // })


    let isActive = false;


    let arm_left1 = document.getElementById("arm-left__default");
    let arm_left2 = document.getElementById("arm-left__active");
    let arm_right1 = document.getElementById("arm-right__default");
    let arm_right2 = document.getElementById("arm-right__active");

    _animate = evt => {

        _moveHead = (() => {
            let head1 = document.getElementById("head__default");
        let head2 = document.getElementById("head__active");

        // Reset head to default state on "mouse up" event.
        head1.style.display  = "block";
        head2.style.display  = "none";

        if ( evt.type === "mousedown" ) {
            return () => {
                head1.style.display  = "none";
                head2.style.display  = "block";
            }
        }

    })();


        // Move Left Arm
        _moveLeftArm = timer => {

            toggleLeftArm = () => {

                if (isActive) {

                    armDown = (() => {
                        arm_left1.style.display  = "block";
                    arm_left2.style.display  = "none";
                    setTimeout(toggleLeftArm, timer)
                })

                    armUp = (() => {
                        arm_left1.style.display  = "none";
                    arm_left2.style.display  = "block";
                    setTimeout(armDown, timer)
                })

                    armUp();

                }
            }

            toggleLeftArm();
        }

        // Move right arm
        _moveRightArm = timer => {

            toggleRightArm = () => {
                if (isActive) {

                    armDown = (() => {
                        arm_right1.style.display  = "block";
                    arm_right2.style.display  = "none";
                    setTimeout(toggleRightArm, timer)
                })

                    armUp = (() => {
                        arm_right1.style.display  = "none";
                    arm_right2.style.display  = "block";
                    setTimeout(armDown, timer)
                })

                    armUp();

                }
            }

            toggleRightArm();
        }

        // Determine if mouse is up or down
        if (evt.type === "mousedown") {
            console.log('KONG ANGRY!')
            isActive = !isActive; // toggle active state to 'true'
            _moveHead();
            _moveLeftArm(200);
            _moveRightArm(250);
        } else {
            isActive = !isActive; // toggle active state to 'false'
            console.log('Kong not like tickle!')
        }
    }

    ['mousedown', 'mouseup'].forEach( evt => {
        window.addEventListener(evt, _animate);
});
}



